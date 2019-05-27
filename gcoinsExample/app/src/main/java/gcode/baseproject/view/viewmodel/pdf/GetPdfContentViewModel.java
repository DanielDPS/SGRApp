package gcode.baseproject.view.viewmodel.pdf;

import android.util.Log;

import java.util.concurrent.Callable;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import gcode.baseproject.domain.model.general.NetworkResponse;
import gcode.baseproject.domain.model.pdf.Format;
import gcode.baseproject.domain.repository.pdf.PdfRepository;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GetPdfContentViewModel extends ViewModel {


    private PdfRepository repository;


    public GetPdfContentViewModel() {
        repository = new PdfRepository();
    }

    Callable<Format> formatCallable(final String formatId) {
        return new Callable<Format>() {
            @Override
            public Format call() throws Exception {
                return repository.getFormatById(formatId);
            }
        };
    }


    public MutableLiveData<NetworkResponse<Format>> networkResponse = new MutableLiveData<>();


    // Cremos el observable asincrono del callable anterior
    private Single<Format> getFormat(String formatId) {
        return Single.fromCallable(formatCallable(formatId));
    }

    public void clear(LifecycleOwner owner) {
        if (networkResponse != null) {
            networkResponse.removeObservers(owner);
            networkResponse = null;
        }
    }

    public void loadPdfData(String formatId) {

        if (networkResponse == null) {
            networkResponse = new MutableLiveData<>();
        }

        networkResponse.postValue(new NetworkResponse
                .Builder<>()
                .withStatus(NetworkResponse.Status.LOADING)
                .build());

        getFormat(formatId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Format>() {
                    @Override
                    public void onSuccess(Format format) {
                        networkResponse.postValue(new NetworkResponse
                                .Builder<>()
                                .withStatus(NetworkResponse.Status.SUCCESS)
                                .withResult(format)
                                .build());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("TAG1", e.getLocalizedMessage());
                        networkResponse.postValue(new NetworkResponse
                                .Builder<>()
                                .withStatus(NetworkResponse.Status.ERROR)
                                .withErrorMessage(e.getLocalizedMessage())
                                .build());
                    }
                });
    }

    public MutableLiveData<NetworkResponse<Format>> getNetworkResponse() {
        return networkResponse;
    }
}
