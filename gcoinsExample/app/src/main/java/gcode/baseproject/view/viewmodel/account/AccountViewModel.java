package gcode.baseproject.view.viewmodel.account;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.model.account.Account;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class  AccountViewModel extends BaseNetworkViewModel {

    private MutableLiveData<Account> accountLiveData = new MutableLiveData<>();

    public AccountViewModel(@NonNull Application application) {
        super(application);
    }

    public void load() {
        getSessionManager()
                .getAccount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAccountObserver());
    }

    private DisposableSingleObserver<Account> getAccountObserver() {
        return new DisposableSingleObserver<Account>() {
            @Override
            public void onSuccess(Account account) {
                accountLiveData.postValue(account);
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ExpiredJwtException) {
                    getSessionManager().onSessionExpiration();
                }
            }
        };
    }
    public MutableLiveData<Account> get() {
        return accountLiveData;
    }
}
