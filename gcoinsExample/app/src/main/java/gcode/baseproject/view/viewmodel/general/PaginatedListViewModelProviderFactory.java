package gcode.baseproject.view.viewmodel.general;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import gcode.baseproject.interactors.service.datasource.parameters.FilterableParameters;
import gcode.baseproject.interactors.service.datasource.general.IPagedListAdapter;

public class PaginatedListViewModelProviderFactory<T, K extends FilterableParameters> implements ViewModelProvider.Factory {

    private Application application;
    private IPagedListAdapter<T, K> filterableService;
    private K parameters;

    public PaginatedListViewModelProviderFactory(Application application, IPagedListAdapter<T,K> service, K parameters) {
        this.application = application;
        this.filterableService = service;
        this.parameters = parameters;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PaginatedListViewModel<>(application, filterableService, parameters);
    }
}
