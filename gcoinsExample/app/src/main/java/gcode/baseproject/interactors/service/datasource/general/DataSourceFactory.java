package gcode.baseproject.interactors.service.datasource.general;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import gcode.baseproject.interactors.service.datasource.parameters.FilterableParameters;


public class DataSourceFactory<T, K extends FilterableParameters> extends DataSource.Factory<Integer, T> {

    private BaseDataSource<T, K> dataSource;

    public DataSourceFactory(BaseDataSource<T, K> dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public DataSource<Integer, T> create() {
        return dataSource;
    }

    public BaseDataSource<T, K> getDataSource() {
        return dataSource;
    }
}
