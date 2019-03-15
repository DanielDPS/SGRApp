package gcode.baseproject.domain.repository.account;

import android.util.Log;


import gcode.baseproject.interactors.api.NetworkManager;
import gcode.baseproject.interactors.api.account.LoginService;
import gcode.baseproject.domain.model.account.TokenResponse;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class LoginRepository implements ILoginRepository {


    private LoginService service;

    public LoginRepository() {

        service = NetworkManager.getInstance().create(LoginService.class);
    }

    @Override
    public Single<String> getTokenByUsernameAndPassword(String username, String password) {
        return service
                .getTokenByUsernameAndPassword( username, password)
                .map(new Function<TokenResponse, String>() {
                    @Override
                    public String apply(TokenResponse tokenResponse) throws Exception {
                        Log.d("TAG1", tokenResponse.getToken());
                        return tokenResponse.getToken();
                    }
                });
    }
}
