package gcode.baseproject.interactors.api.data.fileHeaders;

import java.util.List;

import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeaderResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FileHeaderService {
    @POST("datos_formatos/get_files_headers")
    @FormUrlEncoded
    Single<FileHeaderResponse> getFileHeaders(@Header("authorization") String authToken,@Field("id_data_format") String idFormatData);

    @POST("datos_formatos/get_files_headers")
    @FormUrlEncoded
    List<FileHeaderResponse> getFileHeaderFromAPI(@Header("authorization") String authToken, @Field("id_data_format") String idFormatData);

}
