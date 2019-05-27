package gcode.baseproject.interactors.api.data.headers;

import gcode.baseproject.domain.model.dataAPI.headers.HeaderResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HeaderService {

    @POST("datos_formatos/get_headers")
    @FormUrlEncoded
    Single<HeaderResponse> getHeaders(@Header("authorization") String authToken, @Field("last_update") String lastUpdate,@Field("id_format") String idFormat);

}
