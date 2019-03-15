package gcode.baseproject.interactors.service.account;

import java.util.concurrent.TimeUnit;

import gcode.baseproject.domain.repository.account.SessionManager;
import io.reactivex.Completable;

public class LoadingService {

    private SessionManager sessionManager;
    //construct
    public LoadingService(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Completable load() {
        return sessionManager
                .isCurrentSessionValid()
                .delay(1000, TimeUnit.MILLISECONDS);

    }
}
