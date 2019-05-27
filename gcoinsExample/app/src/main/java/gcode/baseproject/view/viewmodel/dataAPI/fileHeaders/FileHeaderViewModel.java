package gcode.baseproject.view.viewmodel.dataAPI.fileHeaders;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.R;
import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeader;
import gcode.baseproject.domain.repository.dataAPI.fileHeaders.FileHeaderRepository;
import gcode.baseproject.domain.repository.dataAPI.fileHeaders.IFileHeaderRepository;
import gcode.baseproject.view.utils.ErrorUtils;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FileHeaderViewModel extends BaseNetworkViewModel {

    private MutableLiveData<List<FileHeader>> mgetFileHeader= new MutableLiveData<>();
    IFileHeaderRepository iFileHeaderRepository;
    public FileHeaderViewModel(@NonNull Application application) {
        super(application);
        iFileHeaderRepository = new FileHeaderRepository();
    }

    public  void ClearFileHeaders(LifecycleOwner owner){
        if (mgetFileHeader != null){
            mgetFileHeader.removeObservers(owner);
            mgetFileHeader= null;
        }
    }
    public  void LoadFileHeaders(final String idFormatData){
        if (mgetFileHeader ==null){
            mgetFileHeader=new MutableLiveData<>();
        }
        Single<List<FileHeader>> listSingle =getSessionManager().getToken()
                .flatMap(new Function<String, Single< List<FileHeader>>>() {
                    @Override
                    public Single< List<FileHeader>> apply(String token) throws Exception {
                        return iFileHeaderRepository.getFileHeaders(token,idFormatData);
                    }
                });
        listSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObservableFileHeaders());
    }
    private DisposableSingleObserver<List<FileHeader>> getObservableFileHeaders(){
        return new DisposableSingleObserver<List<FileHeader>>() {
            @Override
            public void onSuccess(List<FileHeader> fileHeaders) {
                mgetFileHeader.postValue(fileHeaders);
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
                            //Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
                            Log.e("TAG10",message);
                            break;
                    }
                    String message = ErrorUtils.getErrorMessage(httpException.response().errorBody());
                    //Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
                    Log.e("TAG10",message);
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
    public  MutableLiveData<List<FileHeader>> mGetLiveFileHeaders(){
        return  mgetFileHeader;
    }




}
