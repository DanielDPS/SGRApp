package gcode.baseproject.interactors.api.format;

import gcode.baseproject.domain.model.format.FormatResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FormatService {

    @POST("formatos/get_formats")
    @FormUrlEncoded
    Single<FormatResponse> getFormatsAndSections(@Header("authorization") String authToken, @Field("last_update")String lastUpdate);
}
