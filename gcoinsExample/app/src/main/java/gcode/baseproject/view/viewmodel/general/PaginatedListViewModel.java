package gcode.baseproject.view.viewmodel.general;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import gcode.baseproject.domain.model.general.NetworkResponse;
import gcode.baseproject.interactors.service.datasource.general.BaseDataSource;
import gcode.baseproject.interactors.service.datasource.general.DataSourceFactory;
import gcode.baseproject.interactors.service.datasource.general.IPagedListAdapter;
import gcode.baseproject.interactors.service.datasource.parameters.FilterableParameters;


public class PaginatedListViewModel<T, K extends FilterableParameters> extends BaseNetworkViewModel {

    // Data Source
    protected IPagedListAdapter<T, K> pagedListAdapter;
    protected DataSourceFactory<T, K> dataSourceFactory;
    protected BaseDataSource<T, K> dataSource;
    protected LiveData<PagedList<T>> dataSet;

    // Paging Builder
    PagedList.Config pagedListConfig;

    private K parameters;

    public PaginatedListViewModel(@NonNull Application application, @NonNull IPagedListAdapter<T, K> filterableService, K parameters) {

        super(application);
        this.pagedListAdapter = filterableService;
        this.parameters = parameters;
        this.dataSource = new BaseDataSource
                .Builder<>(filterableService)
                .withParameters(parameters)
                .build();

        this.dataSourceFactory = new DataSourceFactory<>(dataSource);

        pagedListConfig = new PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .build();
    }

    public void load() {
        dataSet = new LivePagedListBuilder(dataSourceFactory, pagedListConfig).build();
    }

    public LiveData<PagedList<T>> getDataset() {
        return dataSet;
    }

    public MutableLiveData<NetworkResponse> getInitialLoadStatus() {
        return dataSourceFactory.getDataSource().getInitialLoadStateLiveData();
    }

    public MutableLiveData<NetworkResponse> getPaginatedLoadStatus() {
        return dataSourceFactory.getDataSource().getPaginatedNetworkStateLiveData();
    }

    public void retry() {
        dataSourceFactory.getDataSource().retry();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        dataSourceFactory.getDataSource().clear();
    }



    private void clear(LifecycleOwner lifecycleOwner) {
        getInitialLoadStatus().removeObservers(lifecycleOwner);
        getPaginatedLoadStatus().removeObservers(lifecycleOwner);
        dataSourceFactory.getDataSource().clear();
        dataSource = null;
        dataSourceFactory = null;
        dataSet = null;
    }

    public void recreateList(LifecycleOwner lifecycleOwner) {
        clear(lifecycleOwner);
        this.dataSource = new BaseDataSource
                .Builder<>(pagedListAdapter)
                .withParameters(parameters)
                .build();
        this.dataSourceFactory = new DataSourceFactory<>(dataSource);
        this.load();
    }


    public void filter(LifecycleOwner lifecycleOwner, String query) {
        clear(lifecycleOwner);
        this.dataSource = new BaseDataSource
                .Builder<>(pagedListAdapter)
                .withParameters(parameters)
                .withQuery(query)
                .build();

        this.dataSourceFactory = new DataSourceFactory<>(dataSource);
        this.load();
    }



}
