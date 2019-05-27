package gcode.baseproject.interactors.api.data.addFile;

import gcode.baseproject.domain.model.dataAPI.addFile.AddFileResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddFileService {

    @POST("datos_formatos/add_file")
    @FormUrlEncoded
    Single<AddFileResponse>addFile(@Header("authorization")String authToken, @Field("id_data_format")String idFormatData,
                                   @Field("status")int status,@Field("file_type") String fileType,@Field("file_base64")String fileBase64);
}
