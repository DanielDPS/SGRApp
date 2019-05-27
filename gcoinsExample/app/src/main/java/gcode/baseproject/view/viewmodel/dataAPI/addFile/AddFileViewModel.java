package gcode.baseproject.view.viewmodel.dataAPI.addFile;

import android.app.Application;
import android.widget.Toast;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.R;
import gcode.baseproject.domain.repository.dataAPI.addFile.AddFileRepository;
import gcode.baseproject.domain.repository.dataAPI.addFile.IAddFileRepository;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;
import gcode.baseproject.view.utils.ErrorUtils;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AddFileViewModel extends BaseNetworkViewModel {


    private MutableLiveData<String >mgetAddFile = new MutableLiveData<>();
    private IAddFileRepository iAddFileRepository;
    public AddFileViewModel(@NonNull Application application) {
        super(application);
        iAddFileRepository = new AddFileRepository();
    }
    public  void ClearAddFile(LifecycleOwner owner){
        if (mgetAddFile != null){
            mgetAddFile.removeObservers(owner);
            mgetAddFile=null;
        }
    }



    public  void AddFile(final FormatDataEntity formatData, final FileDataEntity file, final String encodedFile){
        if (mgetAddFile ==null){
            mgetAddFile= new MutableLiveData<>();
        }
        Single<String > addFileApi =getSessionManager().getToken()
                .flatMap(new Function<String, SingleSource< String>>() {
                    @Override
                    public SingleSource< String> apply(String token) throws Exception {
                        return iAddFileRepository.addFilePdf(token,formatData,file,encodedFile);
                    }
                });
        addFileApi.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObservableAddFile());
    }
    private DisposableSingleObserver<String> getObservableAddFile(){
        return new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String message) {
                mgetAddFile.postValue(message);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();


                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    int statusCode = httpException.code();
                    switch (statusCode) {
                        case HttpURLConnection.HTTP_UNAUTHORIZED:
                            getSessionManager().onSessionDouble();
                            break;
                        case HttpURLConnection.HTTP_BAD_REQUEST:
                            String message = ErrorUtils.getErrorMessage(httpException.response().errorBody());
                            Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();

                            break;
                    }
                    String message = ErrorUtils.getErrorMessage(httpException.response().errorBody());
                    Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
                } else if (e instanceof UnknownHostException) {
                    Toast.makeText(
                            getApplication(),
                            R.string.socket_time_out_exception,
                            Toast.LENGTH_SHORT).show();
                } else if (e instanceof ConnectException) {
                    Toast.makeText(
                            getApplication(),
                            R.string.socket_time_out_exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    public  MutableLiveData<String> mGetAddFile(){
        return  mgetAddFile;
    }
}
