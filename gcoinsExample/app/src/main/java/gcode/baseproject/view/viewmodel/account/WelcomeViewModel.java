package gcode.baseproject.view.viewmodel.account;

import android.app.Application;
import android.widget.Toast;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;
import androidx.lifecycle.MutableLiveData;

import gcode.baseproject.R;
import gcode.baseproject.domain.model.account.Account;
import gcode.baseproject.domain.model.welcome.Greeting;
import gcode.baseproject.domain.repository.welcome.IWelcomeRepository;
import gcode.baseproject.domain.repository.welcome.WelcomeRepository;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WelcomeViewModel extends BaseNetworkViewModel {

    private final MutableLiveData<Greeting> mGreeting  = new MutableLiveData<>();

    private Account accountUser;

    public Account getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(Account accountUser) {
        this.accountUser = accountUser;
    }

    private IWelcomeRepository mWelcomeRepository;

    public WelcomeViewModel(Application application) {
        super(application);
        mWelcomeRepository = new WelcomeRepository();
    }


    public void loadGreeting() {
        if (mGreeting.getValue() == null) {
            Single<Greeting> greetingRequest = getSessionManager()
                    .getToken()
                    .flatMap(new Function<String, Single<Greeting>>() {
                        @Override
                        public Single<Greeting> apply(String token) {
                            return mWelcomeRepository.getWelcomeGreeting(token);
                        }
                    });

            greetingRequest
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getGreetingObserver());
        }
    }

    private DisposableSingleObserver<Greeting> getGreetingObserver() {
        return new DisposableSingleObserver<Greeting>() {
            @Override
            public void onSuccess(Greeting greeting) {
                mGreeting.postValue(greeting);
            }

            @Override
            public void onError(Throwable e) {

                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    int statusCode = httpException.code();
                    switch (statusCode) {
                        case HttpURLConnection.HTTP_UNAUTHORIZED:
                            getSessionManager().onSessionDouble();
                            break;
                    }
                }
                else if (e instanceof UnknownHostException) {
                    Toast.makeText(
                            getApplication(),
                            R.string.socket_time_out_exception,
                            Toast.LENGTH_SHORT).show();
                } else if (e instanceof ConnectException) {
                    Toast.makeText(
                            getApplication(),
                            R.string.socket_time_out_exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    public MutableLiveData<Greeting> getGreeting() {
        return mGreeting;
    }
}
