package gcode.baseproject.view.ui.general;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import gcode.baseproject.databinding.LoadingFragmentBinding;
import gcode.baseproject.view.ui.account.MainActivity;
import gcode.baseproject.view.ui.welcome.WelcomeFragment;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;

public class LoadingFragment extends BaseFragment {

    public static LoadingFragment newInstance() {
        LoadingFragment fragment = new LoadingFragment();
        return fragment;
    }

    private LoadingFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = LoadingFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @NonNull
    @Override
    public String getFragmentTag() {
        return WelcomeFragment.class.getName();
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        return new ToolbarBuilder.Builder(binding.toolbar).withBackButton(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity())
                        .getNavigationManager()
                        .removeFragmentFromBackStack();
            }
        }).build();
    }
}
