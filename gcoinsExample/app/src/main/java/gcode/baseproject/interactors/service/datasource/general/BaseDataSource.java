package gcode.baseproject.interactors.service.datasource.general;

import android.util.Log;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.ItemKeyedDataSource;
import gcode.baseproject.domain.model.general.NetworkResponse;
import gcode.baseproject.interactors.service.datasource.parameters.FilterableParameters;
import gcode.baseproject.interactors.service.datasource.parameters.NullFilterableParameters;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BaseDataSource<T, K extends FilterableParameters> extends ItemKeyedDataSource<Integer, T> {

    protected int pageNumber;
    protected int elementsPerPage;
    protected CompositeDisposable compositeDisposable;

    private MutableLiveData<NetworkResponse> initialLoadStateLiveData;
    private MutableLiveData<NetworkResponse> paginatedLoadStateLiveData;

    // Repository
    protected IPagedListAdapter<T, K> filterableService;

    // For Retry
    private LoadParams<Integer> params;
    private LoadCallback<T> callback;

    // Query
    private String query;

    // Callbacks for what ever they are


    // Parameters
    private K parameters;


    public static class Builder<T, K extends FilterableParameters> {

        private String query = "";
        private IPagedListAdapter<T, K> pagedListAdapter;
        private K parameters = (K)new NullFilterableParameters();

        public Builder(IPagedListAdapter<T, K> filterableService) {
            this.pagedListAdapter = filterableService;
        }

        public Builder withQuery(String query) {
            this.query = query;
            return this;
        }

        public Builder withParameters(K parameters) {
            this.parameters = parameters;
            return this;
        }

        public BaseDataSource<T, K> build() {
            return new BaseDataSource<>(this);
        }
    }

    private BaseDataSource(Builder builder) {
        filterableService = builder.pagedListAdapter;
        query = builder.query;

        parameters =  (K) builder.parameters;
        pageNumber = 1;
        elementsPerPage = 10;
        compositeDisposable = new CompositeDisposable();

        initialLoadStateLiveData = new MutableLiveData<>();
        paginatedLoadStateLiveData = new MutableLiveData<>();

    }

    private SingleObserver<List<T>> getInitialDataSourceObserver(final LoadInitialCallback<T> callback) {
        return new SingleObserver<List<T>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<T> t) {
                initialLoadStateLiveData.postValue(new NetworkResponse
                        .Builder()
                        .withStatus(NetworkResponse.Status.SUCCESS)
                        .build());
                callback.onResult(t);
                pageNumber++;
            }

            @Override
            public void onError(Throwable e) {
                initialLoadStateLiveData.postValue(new NetworkResponse
                        .Builder()
                        .withStatus(NetworkResponse.Status.ERROR)
                        .withError(e).build());
                Log.e("TAG1", e.getMessage());
            }
        };
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<T> callback) {
        Log.d("TAG1", "Fetching first page :  " + pageNumber);
        initialLoadStateLiveData.postValue
                (new NetworkResponse.Builder()
                .withStatus(NetworkResponse.Status.LOADING)
                .build());
        filterableService.getCollection(parameters, elementsPerPage, pageNumber, query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getInitialDataSourceObserver(callback));
    }

    private SingleObserver<List<T>> getAfterDataSourceObserver(final LoadCallback<T> callback) {
        return new SingleObserver<List<T>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<T> t) {
                paginatedLoadStateLiveData.postValue(new NetworkResponse
                        .Builder()
                        .withStatus(NetworkResponse.Status.SUCCESS)
                        .build());
                pageNumber++;
                callback.onResult(t);
            }

            @Override
            public void onError(Throwable e) {
                paginatedLoadStateLiveData.postValue(
                        new NetworkResponse
                                .Builder()
                                .withStatus(NetworkResponse.Status.ERROR)
                                .withError(e)
                                .build());
            }
        };
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<T> callback) {
        this.params = params;
        this.callback = callback;

        Log.d("TAG1", "Fetching next page: " + pageNumber);

        paginatedLoadStateLiveData.postValue(new NetworkResponse
                .Builder()
                .withStatus(NetworkResponse.Status.LOADING)
                .build());

        filterableService.getCollection(parameters, elementsPerPage, params.key , query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getAfterDataSourceObserver(callback));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<T> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull T item) {
        return pageNumber;
    }



    public void clear() {
        pageNumber = 1;
        compositeDisposable.clear();
    }

    public void retry() {
        loadAfter(params, callback);
    }

    public MutableLiveData<NetworkResponse> getInitialLoadStateLiveData() {
        return initialLoadStateLiveData;
    }

    public MutableLiveData<NetworkResponse> getPaginatedNetworkStateLiveData() {
        return paginatedLoadStateLiveData;
    }

}
