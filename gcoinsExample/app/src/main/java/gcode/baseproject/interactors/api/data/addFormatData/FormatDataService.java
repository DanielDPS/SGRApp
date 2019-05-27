package gcode.baseproject.interactors.api.data.addFormatData;

import gcode.baseproject.domain.model.dataAPI.addFormatData.FormatDataResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FormatDataService {

    @POST("datos_formatos/add_data_format")
    @FormUrlEncoded
    Single<FormatDataResponse> addFormatData(@Header("authorization") String authToken, @Field("id") String idFormatData, @Field("id_format") String idFormat,
                                             @Field("id_customer") String idCustomer, @Field("last_update")String lastUpdate, @Field("identificator")String identifier,
                                             @Field("start") String initialDate, @Field("end")String endDate, @Field("status") int status);
}
