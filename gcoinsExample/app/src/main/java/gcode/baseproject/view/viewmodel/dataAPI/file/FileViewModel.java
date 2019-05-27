package gcode.baseproject.view.viewmodel.dataAPI.file;

import android.app.Application;
import android.util.Log;
import android.util.MutableBoolean;

import com.itextpdf.text.pdf.PRIndirectReference;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.repository.dataAPI.file.FileRepository;
import gcode.baseproject.domain.repository.dataAPI.file.IFileRepository;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FileViewModel extends BaseNetworkViewModel {



    private MutableLiveData<String> mgetEncodedPath= new MutableLiveData<>();
    private IFileRepository iFileRepository;
    public FileViewModel(@NonNull Application application) {
        super(application);
        iFileRepository =new FileRepository();
    }
    public  void ClearEncodedPath(LifecycleOwner owner){
        if (mgetEncodedPath!=null){
            mgetEncodedPath.removeObservers(owner);
            mgetEncodedPath= null;
        }
    }
    public  void LoadEncodedPath(final String idFormatData){
        if (mgetEncodedPath==null){
            mgetEncodedPath= new MutableLiveData<>();
        }
        Single<String > encodedPath =getSessionManager().getToken()
                .flatMap(new Function<String, SingleSource< String>>() {
                    @Override
                    public SingleSource< String> apply(String token) throws Exception {
                        return iFileRepository.getFileById(token,idFormatData);
                    }
                });
        encodedPath.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObservableFile());
    }
    private DisposableSingleObserver<String >getObservableFile(){
        return new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String encodedPath) {
                mgetEncodedPath.postValue(encodedPath);
                Log.e("TAG1","SI POSTEA");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e("TAG1",e.getLocalizedMessage());
            }
        };
    }
    public  MutableLiveData<String >mGetFile(){
        return  mgetEncodedPath;
    }



}
