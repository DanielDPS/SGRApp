package gcode.baseproject.view.ui.welcome;

import android.content.Context;
import android.content.res.Configuration;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.joda.time.LocalDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import gcode.baseproject.R;
import gcode.baseproject.databinding.WelcomeFragmentBinding;
import gcode.baseproject.domain.model.account.Account;
import gcode.baseproject.domain.model.welcome.Greeting;
import gcode.baseproject.interactors.date.CurrentDate;
import gcode.baseproject.interactors.session.SessionUser;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.utils.CircleTransformation;
import gcode.baseproject.view.viewmodel.account.AccountViewModel;
import gcode.baseproject.view.viewmodel.account.WelcomeViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.NullToolbarBuilder;

import static java.time.format.DateTimeFormatter.ofPattern;

public class WelcomeFragment extends BaseFragment {

    private WelcomeFragmentBinding mWelcomeBinding;


    public static WelcomeFragment newInstance() {

        return new WelcomeFragment();
    }

     private AccountViewModel accountViewModel;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         accountViewModel= ViewModelProviders.of(this).get(AccountViewModel.class);
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mWelcomeBinding = WelcomeFragmentBinding.inflate(inflater, parent, false);
        return mWelcomeBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        accountViewModel.load();
        accountViewModel.get()
                .observe(this,getAccountObserver());
    }

    private  Observer<Account> getAccountObserver(){
        return new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                //LocalDateTime ldt = LocalDateTime.now().plusDays(1);

                SessionUser.getInstance().setAccount(account);
                Picasso.get()
                        .load(account.getImage())
                        .transform(new CircleTransformation())
                        .into(mWelcomeBinding.welcomeImage);

                mWelcomeBinding.welcomeMessage.setText("Bienvenido "+account.getName());
                mWelcomeBinding.dateMessage.setText(CurrentDate.showDate());



            }
        };
    }



    @Override
    public String getFragmentTag() {
        return WelcomeFragment.class.getName();
    }

    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        return new NullToolbarBuilder();
    }

}
