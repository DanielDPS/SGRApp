package gcode.baseproject.view.viewmodel.account;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import gcode.baseproject.domain.model.permissions.Module;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;


public class HomeViewModel extends BaseNetworkViewModel {

    private int backPressedCounter = 0;

    @Nullable
    private Module currentModule;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void setCurrentModule(Module module) {
        currentModule = module;
    }

    @Nullable
    public Module getCurrentModule() {
        return currentModule;
    }

    public boolean shouldLogOut() {
        return backPressedCounter == 2;
    }


    public void resetBackPressedCounter() {
        backPressedCounter = 0;
    }

    public void incrementBackPressedCounter() {
        backPressedCounter++;
    }

    public void logOut() {
        getSessionManager().logOut();
    }

}
