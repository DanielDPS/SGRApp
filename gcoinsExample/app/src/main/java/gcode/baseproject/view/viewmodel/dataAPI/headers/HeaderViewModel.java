package gcode.baseproject.view.viewmodel.dataAPI.headers;

import android.app.Application;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.model.dataAPI.headers.Header;
import gcode.baseproject.domain.repository.dataAPI.headers.HeaderRepository;
import gcode.baseproject.domain.repository.dataAPI.headers.IHeaderRepository;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class HeaderViewModel extends BaseNetworkViewModel {


    private MutableLiveData<List<Header>> mgetHeaders = new MutableLiveData<>();
    IHeaderRepository iHeaderRepository ;
    public HeaderViewModel(@NonNull Application application) {
        super(application);
        iHeaderRepository = new HeaderRepository();
    }
    public  void ClearHeaders(LifecycleOwner owner){
        if (mgetHeaders!=null){
            mgetHeaders.removeObservers(owner);
            mgetHeaders= null;
        }
    }
    public void getHeadersJSON(final String lastUpdate, final String idFormat){

        if (mgetHeaders==null){
            mgetHeaders = new MutableLiveData<>();
        }
        Single<List<Header>> listHeaders =getSessionManager().getToken()
                .flatMap(new Function<String, SingleSource<? extends List<Header>>>() {
                    @Override
                    public SingleSource<? extends List<Header>> apply(String token) throws Exception {
                        return iHeaderRepository.getHeaderLis(token,lastUpdate,idFormat);
                    }
                });
        listHeaders.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObservableHeaders());
    }
    private DisposableSingleObserver<List<Header>> getObservableHeaders(){
        return  new DisposableSingleObserver<List<Header>>() {
            @Override
            public void onSuccess(List<Header> headers) {
                mgetHeaders.postValue(headers);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e("getObservableHeaders",e.getLocalizedMessage());
            }
        };
    }
    public  MutableLiveData<List<Header>> mGetLiveHeaders(){
        return  mgetHeaders;
    }
}
