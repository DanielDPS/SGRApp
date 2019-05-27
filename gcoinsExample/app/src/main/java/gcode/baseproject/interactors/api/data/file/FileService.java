package gcode.baseproject.interactors.api.data.file;
import gcode.baseproject.domain.model.dataAPI.file.FileResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FileService {
    @POST("datos_formatos/get_file")
    @FormUrlEncoded
    Single<FileResponse> getFileByIdFormatData(@Header("authorization")String authToken, @Field("id_file")String idFormatData);

}
