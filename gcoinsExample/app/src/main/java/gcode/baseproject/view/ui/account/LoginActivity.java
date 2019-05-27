package gcode.baseproject.view.ui.account;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import gcode.baseproject.R;
import gcode.baseproject.databinding.ActivityLoginBinding;
import gcode.baseproject.domain.model.general.NetworkResponse;
import gcode.baseproject.view.viewmodel.account.CredentialsViewModel;
import gcode.baseproject.view.viewmodel.account.DoLoginViewModel;
import gcode.baseproject.view.viewmodel.general.LoadingViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    // View Models
    private DoLoginViewModel doLoginViewModel;
    private CredentialsViewModel credentialsViewModel;
    private LoadingViewModel loadingRequestViewModel;

    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void setUpLoginViewModel() {
        doLoginViewModel = ViewModelProviders.of(this).get(DoLoginViewModel.class);
    }

    private void setUpCredentialsViewModel() {
        credentialsViewModel = ViewModelProviders.of(this).get(CredentialsViewModel.class);
        binding.setCredentialsViewModel(credentialsViewModel);
    }

    private void setUpLoadingViewModel() {
        loadingRequestViewModel = ViewModelProviders.of(this).get(LoadingViewModel.class);
        binding.setLoadingViewModel(loadingRequestViewModel);
    }

    private void setClickListener() {
        binding.setPresenter(new LoginPresenter(this));
    }

    private void startHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private String getErrorMessage(Throwable e) {
        e.printStackTrace();
        String message = "";
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            switch (code) {
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    message = getString(R.string.account_wrong_credentials);
                    break;
            }
        } else if (e instanceof UnknownHostException) {
            message = getString(R.string.socket_time_out_exception);
        } else if (e instanceof ConnectException) {
            message = getString(R.string.socket_time_out_exception);
        }
        return message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
        setUpLoginViewModel();
        setUpCredentialsViewModel();
        setUpLoadingViewModel();
        setClickListener();

        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
    }


    private Observer<NetworkResponse> getNetworkStateObserver() {
        return new Observer<NetworkResponse>() {
            @Override
            public void onChanged(NetworkResponse networkResponse) {
                switch (networkResponse.status())
                {
                    case LOADING:
                        loadingRequestViewModel.isLoading.set(true);
                        break;
                    case SUCCESS:
                        loadingRequestViewModel.isLoading.set(false);
                        startHomeActivity();
                        break;
                    case ERROR:
                        loadingRequestViewModel.isLoading.set(false);
                        String errorMessage = getErrorMessage(networkResponse.error());
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    public class LoginPresenter {
        private LifecycleOwner owner;
        public LoginPresenter(LifecycleOwner owner) {
            this.owner = owner;
        }
        public void onSignInClick(View view) {
            doLoginViewModel.signIn(
                    credentialsViewModel.username.get(),
                    credentialsViewModel.password.get());
            doLoginViewModel
                    .getRequestState()
                    .observe(owner, getNetworkStateObserver());
        }
    }

}
