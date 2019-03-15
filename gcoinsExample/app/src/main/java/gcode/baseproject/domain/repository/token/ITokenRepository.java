package gcode.baseproject.domain.repository.token;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ITokenRepository {

    Single<Boolean> accountIsLoggedIn();
    Completable add(String token);
    Completable remove();
    Single<String> get();
}
