package gcode.baseproject.interactors.service.datasource.general;

import java.util.List;

import gcode.baseproject.interactors.service.datasource.parameters.FilterableParameters;
import io.reactivex.Single;

public interface IPagedListAdapter<T, K extends FilterableParameters> {

    Single<List<T>> getCollection(K parameters, int numberOfElements, int pageNumber, String query);
}
