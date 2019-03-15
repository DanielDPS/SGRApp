package gcode.baseproject.domain.repository.account;

import android.app.Application;
import android.content.Intent;
import java.util.HashMap;
import gcode.baseproject.domain.model.permissions.AccessLevel;
import gcode.baseproject.domain.model.account.Account;
import gcode.baseproject.domain.model.permissions.Module;
import gcode.baseproject.domain.model.permissions.ModuleParameters;
import gcode.baseproject.domain.model.token.TokenDecoder;
import gcode.baseproject.domain.repository.token.ITokenRepository;
import gcode.baseproject.view.ui.account.LoginActivity;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SessionManager {

    private Application application;

    private TokenDecoder tokenDecoder;

    private ITokenRepository tokenRepository;

    // Flags For Log Out
    public static final int FLAG_SESSION_EXPIRE = 100;
    public static final int FLAG_SESSION_DOUBLE = 200;
    public static final String TAG_SESSION_FLAG = "sessionError";


    public SessionManager(Application application, ITokenRepository tokenRepository, TokenDecoder tokenDecoder) {
        this.application = application;
        this.tokenRepository = tokenRepository;
        this.tokenDecoder = tokenDecoder;
    }


    public Single<Account> getAccount() {
        return tokenRepository.get().map(new Function<String, Account>() {
            @Override
            public Account apply(String token) throws Exception {
                return tokenDecoder.parseAccount(token);
            }
        });
    }

    public Single<String> getToken() {
        return tokenRepository.get();
    }


    // Log out logic
    private final DisposableCompletableObserver getLogOutObserver() {
        return new DisposableCompletableObserver() {

            @Override
            public void onComplete() {
                Intent intent = new Intent(application, LoginActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                application.startActivity(intent);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        };
    }

    public void logOut() {
        tokenRepository.remove()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getLogOutObserver());
    }

    public Completable isCurrentSessionValid() {
        return tokenRepository.get().flatMapCompletable(new Function<String, Completable>() {
            @Override
            public Completable apply(final String token) throws Exception {
                return Completable.fromRunnable(new Runnable() {
                    @Override
                    public void run() {
                        tokenDecoder.parseAccount(token);
                    }
                });
            }
        });
    }

    public void onSessionExpiration() {
        tokenRepository
                .remove()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSessionExpireObserver(FLAG_SESSION_EXPIRE));
    }

    public void onSessionDouble() {
        tokenRepository
                .remove()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSessionExpireObserver(FLAG_SESSION_DOUBLE));
    }



    private final DisposableCompletableObserver getSessionExpireObserver(final int flag) {
        return new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                Intent intent = new Intent(application, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra(TAG_SESSION_FLAG, flag);
                application.startActivity(intent);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        };
    }

    public Single<AccessLevel> getModuleAccessLevel(final Module module) {
        return tokenRepository.get().map(new Function<String, AccessLevel>() {
            @Override
            public AccessLevel apply(String token) throws Exception {
                return tokenDecoder.parseAccessLevelByModule(token, module );
            }
        });
    }

    public Single<HashMap<Module, AccessLevel>> getMenuAccessLevel() {
        return tokenRepository.get().map(new Function<String, HashMap<Module, AccessLevel>>() {
            @Override
            public HashMap<Module, AccessLevel> apply(String token) throws Exception {
                return tokenDecoder.parseModulesAccessLevel(token);
            }
        });
    }

    public Single<ModuleParameters> getPermissionsByModule(final Module module) {
        return tokenRepository.get().map(new Function<String, ModuleParameters>() {

            @Override
            public ModuleParameters apply(String token) throws Exception {
                return tokenDecoder.parseModule(token, module);
            }

        });
    }


}
