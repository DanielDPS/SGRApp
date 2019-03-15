package gcode.baseproject.view.ui.general;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;

public class ErrorFragment extends BaseFragment {


    // Creation
    public static ErrorFragment newInstance() {
        ErrorFragment fragment = new ErrorFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @NonNull
    @Override
    public String getFragmentTag() {
        return null;
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        return null;
    }


}
