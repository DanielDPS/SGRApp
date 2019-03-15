package gcode.baseproject.view.ui.account;

import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import gcode.baseproject.R;
import gcode.baseproject.databinding.ActivityLoadingBinding;
import gcode.baseproject.view.viewmodel.account.LoadingViewModel;


public class LoadingActivity extends AppCompatActivity {

    private LoadingViewModel loadingViewModel;
    private ActivityLoadingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loading);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        loadingViewModel = ViewModelProviders.of(this).get(LoadingViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //startAnimation();
        loadingViewModel.load();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void startAnimation() {
        Drawable drawableLoaderAnimation = binding.loaderAnim.getDrawable();
        if (drawableLoaderAnimation instanceof AnimatedVectorDrawableCompat) {
            AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat) drawableLoaderAnimation;
            avd.start();
        }
        else if (drawableLoaderAnimation instanceof AnimatedVectorDrawable) {
            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) drawableLoaderAnimation;
            avd.start();
        }
    }
}
