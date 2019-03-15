package gcode.baseproject.view.ui.general;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import gcode.baseproject.view.ui.account.MainActivity;
import gcode.baseproject.view.utils.NavigationManager;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarManager;

public abstract class BaseFragment extends Fragment {


    // Toolbar Manager
    private ToolbarManager toolbarManager;

    @NonNull
    public abstract String getFragmentTag();

    @NonNull
    public abstract BaseToolbarBuilder getFragmentToolbarBuilder();


    protected void prepareToolbar() {
        toolbarManager = new ToolbarManager(getFragmentToolbarBuilder());
        toolbarManager.prepareToolbar();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareToolbar();
    }

    public NavigationManager getNavigationManager() {
        MainActivity activity = (MainActivity) getActivity();
        return activity.getNavigationManager();
    }

}
