package gcode.baseproject.view.viewmodel.account;

import android.app.Application;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import gcode.baseproject.domain.model.permissions.AccessLevel;
import gcode.baseproject.domain.model.permissions.Module;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MenuViewModel extends BaseNetworkViewModel {

    private final MutableLiveData<HashMap<Module, AccessLevel>> menu = new MutableLiveData<>();

    public MenuViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadMenu() {
        getSessionManager()
                .getMenuAccessLevel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAccessLevelObserver());
    }

    private DisposableSingleObserver<HashMap<Module, AccessLevel>> getAccessLevelObserver() {
        return new DisposableSingleObserver<HashMap<Module, AccessLevel>>() {
            @Override
            public void onSuccess(HashMap<Module, AccessLevel> moduleAccessLevelMap) {
                menu.postValue(moduleAccessLevelMap);
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ExpiredJwtException) {
                    getSessionManager().onSessionExpiration();
                }
            }
        };
    }

    public MutableLiveData<HashMap<Module, AccessLevel>> getMenu() {
        return menu;
    }
}