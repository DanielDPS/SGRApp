package gcode.baseproject.view.utils.bindingadapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import gcode.baseproject.R;
import gcode.baseproject.view.utils.CircleTransformation;

public class BindingAdapters {


    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso
                .get()
                .load(imageUrl)
                .transform(new CircleTransformation())
                .into(view);
    }

    @BindingAdapter("bind:isVisible")
    public static void showProgressBar(ProgressBar progressBar, boolean enabled) {
        if (enabled) {
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }


    @BindingAdapter("bind:changeColor")
    public static void changeColor(Chip chip, float dateDifference) {
        @ColorRes int colorRes = R.color.colorProgressBarGreen;
        if (dateDifference == 0) {
            colorRes = R.color.colorProgressBarRed;
        }
        else if (dateDifference < 1.0f) {
            colorRes = R.color.colorProgressBarYellow;
        }
        chip.setChipBackgroundColorResource(colorRes);
    }

    @BindingAdapter("bind:changeTextColor")
    public static void changeTextColor(Chip chip, float dateDifference) {
        Context context = chip.getContext();
        int color = ContextCompat.getColor(context, R.color.colorPrimaryText);
        if (dateDifference <= 2.0f && dateDifference != 0) {
            color = ContextCompat.getColor(context, R.color.colorSecondaryText);
        }
        chip.setTextColor(color);
    }


    @BindingAdapter("bind:hideView")
    public static void showView(View view, boolean enabled) {
        if (enabled) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
