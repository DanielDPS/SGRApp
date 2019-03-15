package gcode.baseproject.view.viewmodel.account;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.interactors.service.account.LoginService;
import gcode.baseproject.domain.model.general.NetworkResponse;
import gcode.baseproject.domain.repository.account.ILoginRepository;
import gcode.baseproject.domain.repository.account.LoginRepository;
import gcode.baseproject.domain.repository.token.ITokenRepository;
import gcode.baseproject.domain.repository.token.TokenRepository;
import gcode.baseproject.view.utils.API;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class DoLoginViewModel extends BaseNetworkViewModel {

    // Service
    private ILoginRepository loginRepository;
    private ITokenRepository tokenRepository;
    private LoginService loginService;


    // Network State
    private MutableLiveData<NetworkResponse> networkStateMutableLiveData = new MutableLiveData<>();

    public DoLoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository();
        tokenRepository = new TokenRepository();
        loginService = new LoginService(loginRepository, tokenRepository);
    }

    public void signIn(String username, String password) {
        networkStateMutableLiveData.postValue(new NetworkResponse
                .Builder()
                .withStatus(NetworkResponse.Status.LOADING)
                .build());

        loginService
                .signIn(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getSignInObserver());
    }

    private DisposableCompletableObserver getSignInObserver() {
        return new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                networkStateMutableLiveData.postValue(new NetworkResponse
                        .Builder()
                        .withStatus(NetworkResponse.Status.SUCCESS)
                        .build());
            }

            @Override
            public void onError(Throwable e) {
                networkStateMutableLiveData.postValue(new NetworkResponse
                        .Builder()
                        .withStatus(NetworkResponse.Status.ERROR)
                        .withError(e).build());
            }
        };
    }

    public MutableLiveData<NetworkResponse> getRequestState() {
        return networkStateMutableLiveData;
    }
}