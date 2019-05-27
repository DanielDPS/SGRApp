package gcode.baseproject.domain.repository.dataAPI.headers;

import java.util.List;

import gcode.baseproject.domain.model.dataAPI.headers.Header;
import io.reactivex.Single;

public interface IHeaderRepository {

    Single<List<Header>> getHeaderLis(String token,String lastUpdate,String idFormat);
 }
