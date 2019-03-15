package gcode.baseproject.interactors.service.account;

import gcode.baseproject.domain.repository.account.ILoginRepository;
import gcode.baseproject.domain.repository.token.ITokenRepository;
import io.reactivex.Completable;
import io.reactivex.functions.Function;

public class LoginService {

    private ITokenRepository tokenRepository;
    private ILoginRepository loginRepository;

    public LoginService(ILoginRepository loginRepository, ITokenRepository tokenRepository) {
        this.loginRepository = loginRepository;
        this.tokenRepository = tokenRepository;
    }

    public Completable signIn(String username, String password) {
        return loginRepository.getTokenByUsernameAndPassword(username, password).flatMapCompletable( new Function<String, Completable>() {
            @Override
            public Completable apply(final String token) throws Exception {
                return tokenRepository.add(token);
            }
        });
    }

}
