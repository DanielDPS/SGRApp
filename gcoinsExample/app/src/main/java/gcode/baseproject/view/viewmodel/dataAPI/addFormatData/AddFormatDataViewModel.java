package gcode.baseproject.view.viewmodel.dataAPI.addFormatData;

import android.app.Application;
import android.app.ProgressDialog;
import android.util.Log;
import com.itextpdf.text.pdf.codec.Base64;
import java.util.List;
import androidx.annotation.NonNull;
import gcode.baseproject.domain.model.dataAPI.addFile.AddFileResponse;
import gcode.baseproject.domain.model.dataAPI.addFormatData.FormatDataResponse;
import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeader;
import gcode.baseproject.domain.model.pdf.Format;
import gcode.baseproject.domain.repository.dataAPI.addFormatData.AddFormatDataRepository;
import gcode.baseproject.domain.repository.dataAPI.addFormatData.IAddFormatDataRepository;
import gcode.baseproject.interactors.date.CurrentDate;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;
import gcode.baseproject.view.ui.format.FormatSectionsFragment;
import gcode.baseproject.view.utils.ErrorUtils;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class AddFormatDataViewModel extends BaseNetworkViewModel {

    private IAddFormatDataRepository iAddFormatDataRepository;
    private String tokenApi;
    private String tokenAPIButton;
    private FormatSectionsFragment context;

    public FormatSectionsFragment getContext() {
        return context;
    }

    public void setContext(FormatSectionsFragment context) {
        this.context = context;
    }

    public AddFormatDataViewModel(@NonNull Application application) {
        super(application);
        iAddFormatDataRepository = new AddFormatDataRepository();
    }

    //BUTTON SAVE INSPECTION (SECTIONS)
    public Single<FormatDataResponse> getSingleAddHeaderFormat(final Format inspection) {
        Single<FormatDataResponse> addFormat = getSessionManager().getToken()
                .flatMap(new Function<String, SingleSource<FormatDataResponse>>() {
                    @Override
                    public SingleSource<FormatDataResponse> apply(String token) throws Exception {
                        tokenApi = token;
                        return iAddFormatDataRepository.addFormatData(token, inspection.getIdFormatData(), inspection.getFkFormat(), inspection.getCustomer().getCId(), inspection.getEdited(), inspection.getIdentifier(), inspection.getEdited(), inspection.getEdited(), inspection.getState01());
                    }
                });
        return addFormat;
    }

    private void SendSecondFileSingle(final String token, final List<FileDataEntity> files, final Format inspection, final ProgressDialog dialog) {
        String encodesString2 = Base64.encodeFromFile(files.get(1).getFile());
        iAddFormatDataRepository.SendFileTwo(token, files.get(1), encodesString2, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AddFileResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(AddFileResponse addFileResponse) {
                        if (addFileResponse.isStatus()) {
                            Log.d("TAG4", "API ARCHIVO 2");
                            iAddFormatDataRepository.UpdateFormat(1,1,CurrentDate.showDate(),inspection.getIdFormatData());
                            SendThridFileSingle(token, files,inspection,dialog);
                        } else {
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        return;
                    }
                });
    }

    private void SendThridFileSingle(String token, List<FileDataEntity> files, final Format inspection, final ProgressDialog dialog) {
        String encodesString3 = Base64.encodeFromFile(files.get(2).getFile());
        iAddFormatDataRepository.SendFileThree(token, files.get(2), encodesString3, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AddFileResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(AddFileResponse addFileResponse) {
                        if (addFileResponse.isStatus()) {
                            Log.d("TAG4", "API ARCHIVO 3");
                            iAddFormatDataRepository.UpdateFormat(1, 2, CurrentDate.showDate(), inspection.getIdFormatData());
                            dialog.dismiss();
                            getContext().getNavigationManager().removeFragmentFromBackStack();
                        } else {
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        return;
                    }
                });

    }

    public Completable addHeaderFormatData(final Format inspection, final ProgressDialog dialog) {
        Completable complete = getSingleAddHeaderFormat(inspection).flatMapCompletable(new Function<FormatDataResponse, CompletableSource>() {
            @Override
            public CompletableSource apply(final FormatDataResponse formatDataResponseAPI) throws Exception {
                return Completable.fromRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (formatDataResponseAPI.isStatus()) {
                            Log.d("TAG4", "ES TRUE");
                            final List<FileDataEntity> files = iAddFormatDataRepository.getFilesByFkFormatData(inspection.getIdFormatData());
                            String encodesString = Base64.encodeFromFile(files.get(0).getFile());
                            iAddFormatDataRepository.SendFileOne(tokenApi, files.get(0), encodesString, 1)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new SingleObserver<AddFileResponse>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                        }

                                        @Override
                                        public void onSuccess(AddFileResponse addFileResponse) {
                                            if (addFileResponse.isStatus() && addFileResponse.getEncodedPathMessage() !=null) {
                                                Log.d("TAG4", "API ARCHIVO 1");
                                                iAddFormatDataRepository.UpdateFormat(1, 1, CurrentDate.showDate(),inspection.getIdFormatData());
                                                SendSecondFileSingle(tokenApi, files, inspection,dialog);
                                            } else {
                                                return;
                                            }
                                        }
                                        @Override
                                        public void onError(Throwable e) {
                                            e.printStackTrace();
                                            return;
                                        }
                                    });

                        } else {
                            Log.d("TAG4","FALSE");
                        }

                    }
                });
            }
        });
        return complete;
    }




    //BUTTON REFRESH FILES TO API
    private void SendSecondFileSingleButton(final String token, final List<FileDataEntity> files, final FormatDataEntity inspection, final ProgressDialog dialog) {
        String encodesString2 = Base64.encodeFromFile(files.get(1).getFile());
        iAddFormatDataRepository.SendFileTwo(token, files.get(1), encodesString2, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AddFileResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(AddFileResponse addFileResponse) {
                        if (addFileResponse.isStatus()) {
                            Log.d("TAG4", "API ARCHIVO 2");
                            iAddFormatDataRepository.UpdateFormat(1,1,CurrentDate.showDate(),inspection.getFdid());
                            SendThridFileSingleButton(token, files,inspection,dialog);
                        } else {
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        return;
                    }
                });
    }

    private void SendThridFileSingleButton(String token, List<FileDataEntity> files, final FormatDataEntity inspection, final ProgressDialog dialog) {
        String encodesString3 = Base64.encodeFromFile(files.get(2).getFile());
        iAddFormatDataRepository.SendFileThree(token, files.get(2), encodesString3, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AddFileResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(AddFileResponse addFileResponse) {
                        if (addFileResponse.isStatus()) {
                            Log.d("TAG4", "API ARCHIVO 3");
                            iAddFormatDataRepository.UpdateFormat(1, 2, CurrentDate.showDate(), inspection.getFdid());
                            dialog.dismiss();
                        } else {
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        return;
                    }
                });

    }

    private Single<FormatDataResponse> getSingleAddHeader2(final FormatDataEntity inspection) {
        Single<FormatDataResponse> addFormat2 = getSessionManager().getToken()
                .flatMap(new Function<String, SingleSource<FormatDataResponse>>() {
                    @Override
                    public SingleSource<FormatDataResponse> apply(String token) throws Exception {
                        tokenAPIButton = token;
                        return iAddFormatDataRepository.addFormatData(token, inspection.getFdid(), inspection.getFkformat(), inspection.getFkCustomer(), inspection.getEdited(), inspection.getIdentifier(), inspection.getEdited(), inspection.getEdited(), inspection.getEstado01());
                    }
                });
        return addFormat2;
    }

    private  void SendFilesAgain(final FormatDataEntity inspection,final ProgressDialog dialog){
        final List<FileDataEntity> files = iAddFormatDataRepository.getFilesByFkFormatData(inspection.getFdid());
        iAddFormatDataRepository.SendFileOne(tokenAPIButton,files.get(0),Base64.encodeFromFile(files.get(0).getFile()),1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AddFileResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onSuccess(AddFileResponse addFileResponse) {


                        Log.d("TAG4", "API ARCHIVO 1 BUTTON");
                        iAddFormatDataRepository.UpdateFormat(1, 1, CurrentDate.showDate(),inspection.getFdid());
                        SendSecondFileSingleButton(tokenApi, files, inspection,dialog);


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("TAG55555",e.getLocalizedMessage());

                        if (e.getLocalizedMessage().equals("HTTP 400 Bad Request")){
                        }

                        //HttpException httpException = (HttpException)e;
                        //String message = ErrorUtils.getErrorMessage(httpException.response().errorBody());
                        //Log.e("TAGAPI",message);

                    }
                });
    }
    public void addHeaderOfButton(final FormatDataEntity inspection, final ProgressDialog dialog) {

        Completable completeAction = getSingleAddHeader2(inspection).flatMapCompletable(new Function<FormatDataResponse, CompletableSource>() {
            @Override
            public CompletableSource apply(final FormatDataResponse formatDataResponse) throws Exception {
                return Completable.fromRunnable(new Runnable() {
                    @Override
                    public void run() {

                        if (formatDataResponse.isStatus()) {
                            Log.d("TAG4","TRUE");
                            SendFilesAgain(inspection,dialog);

                        } else {
                            Log.d("TAG4","FALSE");
                            return;

                        }

                    }
                });
            }
        });
         completeAction.subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new DisposableCompletableObserver() {
                     @Override
                     public void onComplete() {
                         Log.d("TAG4","se completo");
                     }

                     @Override
                     public void onError(Throwable e) {
                         e.printStackTrace();
                         Log.e("TAGCABECERA",e.getLocalizedMessage());
                         if (e.getLocalizedMessage().equals("HTTP 400 Bad Request")){
                             SendFiles(tokenAPIButton,inspection,dialog);
                         }
                         //HttpException httpException = (HttpException)e;
                         //String message = ErrorUtils.getErrorMessage(httpException.response().errorBody());
                     }
                 });

    }

    public void SendFiles(final String token, final FormatDataEntity inspection, final ProgressDialog dialog) {
        final List<FileDataEntity> files = iAddFormatDataRepository.getFilesByFkFormatData(inspection.getFdid());

        Single<List<FileHeader>> headers = iAddFormatDataRepository.getFilesHeadersAPI(token, inspection.getFdid());
        headers.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<FileHeader>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(final List<FileHeader> fileHeaders) {

                        //FILE ONE
                        boolean existsFileOne  = false;
                        for (FileHeader fileHeader : fileHeaders){
                           if (fileHeader.getFileType().equals(files.get(0).getType())){
                               existsFileOne =true;
                           }
                        }
                        if (existsFileOne==false){
                            String encodedfile1=Base64.encodeFromFile(files.get(0).getFile());
                            iAddFormatDataRepository.SendFileOne(token,files.get(0),encodedfile1,1)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new SingleObserver<AddFileResponse>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onSuccess(AddFileResponse addFileResponse) {
                                            if (addFileResponse.isStatus()){
                                                Log.d("TAG4","SI LO AGREGO AL API FILE ONE");
                                                iAddFormatDataRepository.UpdateFormat(1, 1, CurrentDate.showDate(), inspection.getFdid());

                                            }else{
                                                Log.e("TAG4","NO LO AGREGO AL API FILE ONE");
                                            }

                                        }
                                        @Override
                                        public void onError(Throwable e) {
                                            e.printStackTrace();
                                            Log.e("TAGFileOne",e.getLocalizedMessage());
                                            dialog.dismiss();
                                        }
                                    });


                        }else{
                            Log.d("TAG4","FILE 1 EXISTS");


                            boolean existsFileTwo = false;
                            for (FileHeader fileHeader : fileHeaders){
                                if (fileHeader.getFileType().equals(files.get(1).getType())){
                                    existsFileTwo=true;
                                }
                            }
                            if (existsFileTwo==false){
                                iAddFormatDataRepository.SendFileTwo(token,files.get(1),Base64.encodeFromFile(files.get(1).getFile()),1)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new SingleObserver<AddFileResponse>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onSuccess(AddFileResponse addFileResponse) {
                                                if (addFileResponse.isStatus()){
                                                    Log.d("TAG4","API SI TIENE FILE TWO");
                                                    iAddFormatDataRepository.UpdateFormat(1, 1,CurrentDate.showDate(), inspection.getFdid());
                                                    boolean existsFileThree= false;
                                                    for (FileHeader fileHeader : fileHeaders){
                                                        if (fileHeader.getFileType().equals(files.get(2).getType())){
                                                            existsFileThree =true;
                                                        }
                                                    }
                                                    if (existsFileThree==false){
                                                        iAddFormatDataRepository.SendFileThree(token,files.get(2),Base64.encodeFromFile(files.get(2).getFile()),2)
                                                                .subscribeOn(Schedulers.io())
                                                                .observeOn(AndroidSchedulers.mainThread())
                                                                .subscribe(new SingleObserver<AddFileResponse>() {
                                                                    @Override
                                                                    public void onSubscribe(Disposable d) {

                                                                    }

                                                                    @Override
                                                                    public void onSuccess(AddFileResponse addFileResponse) {
                                                                        if (addFileResponse.isStatus()){
                                                                            Log.d("TAG4","API TIENE FILE 3");
                                                                            iAddFormatDataRepository.UpdateFormat(1, 2,CurrentDate.showDate(), inspection.getFdid());
                                                                            dialog.dismiss();
                                                                        }else{
                                                                            Log.e("TAG4","API NO TIENE FILE 3");
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onError(Throwable e) {
                                                                        e.printStackTrace();
                                                                        Log.e("TAGFileTwo",e.getLocalizedMessage());
                                                                    }
                                                                });

                                                    }else{
                                                        Log.d("TAG4","FILE 3 EXISTS");
                                                        return;
                                                    }

                                                }else{
                                                    Log.e("TAG4","API NO TIENE FILE TWO");
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                e.printStackTrace();
                                                Log.e("TAGFiles",e.getLocalizedMessage());
                                                dialog.dismiss();
                                            }
                                        });
                            }else{
                                Log.d("TAG4","FILE 2 EXISTS");
                                boolean existsFileThree= false;
                                for (FileHeader fileHeader : fileHeaders){
                                    if (fileHeader.getFileType().equals(files.get(2).getType())){
                                        existsFileThree =true;
                                    }
                                }
                                if (existsFileThree==false){
                                    iAddFormatDataRepository.SendFileThree(token,files.get(2),Base64.encodeFromFile(files.get(2).getFile()),2)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new SingleObserver<AddFileResponse>() {
                                                @Override
                                                public void onSubscribe(Disposable d) {

                                                }

                                                @Override
                                                public void onSuccess(AddFileResponse addFileResponse) {
                                                    if (addFileResponse.isStatus()){
                                                        Log.d("TAG4","API TIENE FILE 3");
                                                        iAddFormatDataRepository.UpdateFormat(1, 2, inspection.getEndDate(), inspection.getFdid());
                                                        dialog.dismiss();
                                                    }else{
                                                        Log.e("TAG4","API NO TIENE FILE 3");
                                                    }
                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    e.printStackTrace();
                                                    Log.e("TAGFileThree",e.getLocalizedMessage());
                                                }
                                            });

                                }else{
                                    Log.d("TAG4","FILE 3 EXISTS");
                                    return;
                                }

                            }
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("SENDFILES","SENDFILES ERROR");
                        //Log.e("TAGULTIMO", e.getLocalizedMessage());
                        if (e.getLocalizedMessage().equals("HTTP 400 Bad Request")){
                            SendFilesAgain(inspection,dialog);
                        }
                        //HttpException httpException = (HttpException) e;
                        //String message = ErrorUtils.getErrorMessage(httpException.response().errorBody());
                        //Log.e("TAGAPI",message);
                    }
                });
    }

}
