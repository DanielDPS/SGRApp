package gcode.baseproject.interactors.api.account;

import gcode.baseproject.domain.model.account.TokenResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import io.reactivex.Single;

public interface LoginService {

    @POST("usuarios/login")
    @FormUrlEncoded
    Single<TokenResponse> getTokenByUsernameAndPassword(@Field("usuario") String user, @Field("contrasena") String password);

}
