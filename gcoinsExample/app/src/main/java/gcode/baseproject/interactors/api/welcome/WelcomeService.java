package gcode.baseproject.interactors.api.welcome;

import gcode.baseproject.domain.model.welcome.WelcomeResponse;
import io.reactivex.Single;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WelcomeService {
    @POST("bienvenida/get")
    Single<WelcomeResponse> getWelcomeGreeting(@Header("authorization") String authToken);
}
