package gcode.baseproject.view.viewmodel.account;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.model.permissions.Module;
import gcode.baseproject.domain.model.permissions.ModuleParameters;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PermissionsViewModel extends BaseNetworkViewModel {

    private final MutableLiveData<ModuleParameters> permissions = new MutableLiveData<>();

    public PermissionsViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadModulePermissions(Module module) {
        getSessionManager()
                .getPermissionsByModule(module)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getModuleObserver());
    }

    public MutableLiveData<ModuleParameters> getModulePermissions() {
        return permissions;
    }

    private DisposableSingleObserver<ModuleParameters> getModuleObserver() {
        return new DisposableSingleObserver<ModuleParameters>() {
            @Override
            public void onSuccess(ModuleParameters moduleParameters) {
                permissions.postValue(moduleParameters);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (e instanceof ExpiredJwtException) {
                    getSessionManager().onSessionExpiration();
                }
            }
        };
    }

}
