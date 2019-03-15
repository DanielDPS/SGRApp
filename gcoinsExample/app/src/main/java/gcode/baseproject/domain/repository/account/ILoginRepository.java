package gcode.baseproject.domain.repository.account;

import io.reactivex.Single;

public interface ILoginRepository {

    Single<String> getTokenByUsernameAndPassword(String username, String password);

}
