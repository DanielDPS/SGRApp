package gcode.baseproject.view.viewmodel.account;

import android.app.Application;
import android.content.Intent;
import java.util.concurrent.TimeUnit;
import androidx.annotation.NonNull;
import gcode.baseproject.view.ui.account.LoginActivity;
import gcode.baseproject.view.ui.account.MainActivity;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoadingViewModel extends BaseNetworkViewModel {

    public LoadingViewModel(@NonNull Application application) {
        super(application);
    }

    public void load() {
        getSessionManager()
                .isCurrentSessionValid()
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSessionVerificationObserver());
    }

    private DisposableCompletableObserver getSessionVerificationObserver() {
        return new DisposableCompletableObserver() {

            @Override
            public void onComplete() {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getApplication().startActivity(intent);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getApplication().startActivity(intent);
            }
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
