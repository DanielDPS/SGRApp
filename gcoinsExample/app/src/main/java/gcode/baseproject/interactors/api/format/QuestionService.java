package gcode.baseproject.interactors.api.format;

import gcode.baseproject.domain.model.format.FormatResponse;
import gcode.baseproject.domain.model.format.QuestionResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface QuestionService {

    @POST("estructura_formatos/get_questions")
    @FormUrlEncoded
    Single<QuestionResponse> getFormatsAndSections(@Header("authorization") String authToken, @Field("last_update")String lastUpdate, @Field("id_section") String idSection);
}
