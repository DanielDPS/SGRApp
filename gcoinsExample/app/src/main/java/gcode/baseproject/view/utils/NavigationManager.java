package gcode.baseproject.view.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import gcode.baseproject.R;
import gcode.baseproject.view.ui.general.BaseFragment;

public class NavigationManager {

    private FragmentManager fragmentManager;
    private int containerId;
    private String lastTag  = null;
    private BackStackEventListener eventListener;

    public NavigationManager(FragmentManager manager, BackStackEventListener eventListener) {
        fragmentManager = manager;
        containerId  = R.id.main_content;
        this.eventListener = eventListener;
    }


    public boolean shouldRemoveFromBackStack() {
        return getBackStackCount() > 1;
    }


    /***
     *
     * Method for handling screen rotation.
     *
     * ***/

    public void init() {
        if (getBackStackCount() < 2) {
            eventListener.onDrawerToggleEnabled();
        }
        else {
            eventListener.onDrawerToggleDisabled();
        }
    }


    public void addFragment(BaseFragment fragment) {
        if (!isFragmentInBackStack(fragment.getFragmentTag())) {
            lastTag = fragment.getFragmentTag();
            fragmentManager
                    .beginTransaction()
                    .addToBackStack(fragment.getTag())
                    .replace(containerId, fragment, fragment.getFragmentTag())
                    .commit();

        }

        if (getBackStackCount() >= 1) {
            eventListener.onDrawerToggleDisabled();
        }
    }

    public void removeFragmentFromBackStack() {
        fragmentManager.popBackStack();
        if (getBackStackCount() == 2) {
            eventListener.onDrawerToggleEnabled();
        }
    }

    public boolean isFragmentInBackStack(String tag) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        return fragment != null;
    }


    public int getBackStackCount() {
        return fragmentManager.getBackStackEntryCount();
    }


    public interface BackStackEventListener {
        void onDrawerToggleDisabled();
        void onDrawerToggleEnabled();
    }

}
