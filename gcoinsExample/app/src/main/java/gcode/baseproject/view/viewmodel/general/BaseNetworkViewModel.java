package gcode.baseproject.view.viewmodel.general;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import gcode.baseproject.MyApplication;
import gcode.baseproject.domain.repository.account.SessionManager;

public class BaseNetworkViewModel extends AndroidViewModel {

    private SessionManager sessionManager;

    public BaseNetworkViewModel(@NonNull Application application) {
        super(application);
        sessionManager = ((MyApplication)application).getSessionManager();
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void onTokenExpiration() {
        getSessionManager().onSessionExpiration();
    }

    public void onSessionDouble() {
        getSessionManager().onSessionDouble();
    }

}
