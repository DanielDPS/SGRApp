package gcode.baseproject.view.ui.format;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatDataFragmentBinding;
import gcode.baseproject.interactors.adapters.FormatDataAdapter;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.view.ui.example.ExampleFragment;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.viewmodel.format.FormatDataViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;

public class FormatDataFragment extends BaseFragment {

    private  static String FORMAT_ARGS ="format_args";
    private FormatDataFragmentBinding formatDataFragmentBinding;
    private FormatEntity format;
    private FormatDataViewModel formatDataViewModel;
    public static final FormatDataFragment newInstance(FormatEntity format){
        FormatDataFragment formatDataFragment = new FormatDataFragment();
        Bundle args= new Bundle();
        args.putSerializable(FORMAT_ARGS,format);
        formatDataFragment.setArguments(args);
        return formatDataFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null ){
            format = (FormatEntity)getArguments().getSerializable(FORMAT_ARGS);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        formatDataViewModel = ViewModelProviders.of(this).get(FormatDataViewModel.class);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        formatDataFragmentBinding = FormatDataFragmentBinding.inflate(inflater,container,false);
        return formatDataFragmentBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Toast.makeText(getContext(),format.getCId(),Toast.LENGTH_SHORT).show();

    }
    @NonNull
    @Override
    public String getFragmentTag() {

        return FormatDataFragment.class.getName();
    }
    void RefreshAll(){
        final FormatDataFragment dataFragment = this;
        formatDataViewModel.ClearFormatData(this);
        formatDataViewModel.LoadFormatData();
        formatDataViewModel.getGetFormatData()
                .observe(this, new Observer<List<FormatDataEntity>>() {
                    @Override
                    public void onChanged(List<FormatDataEntity> formatDataEntities) {

                        formatDataFragmentBinding.formatDataRecycler.setHasFixedSize(true);
                        formatDataFragmentBinding.formatDataRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                        FormatDataAdapter formatDataAdapter = new FormatDataAdapter(formatDataEntities,dataFragment);
                        formatDataFragmentBinding.formatDataRecycler.setAdapter(formatDataAdapter);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        RefreshAll();
    }
    private  List<Integer> getMenuItems(){
        List<Integer> menuItems= new ArrayList<>();
        menuItems.add(R.id.Format_data_refresh);
        menuItems.add(R.id.Format_data_view_pdf);
        menuItems.add(R.id.Format_data_add);
        return menuItems;
    }

    private  List<MenuItem.OnMenuItemClickListener> getMenuClicks(){
        List<MenuItem.OnMenuItemClickListener> listeners = new ArrayList<>();
        listeners.add(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                RefreshAll();
                return false;
            }
        });
        listeners.add(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getNavigationManager().addFragment(new ExampleFragment());
                return false;
            }
        });
        listeners.add(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getNavigationManager().addFragment(FormatDataModuleFragment.newInstance(format));
                return false;
            }
        });
        return listeners;
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(formatDataFragmentBinding.toolbar)
                .withTitle("Inspeccion IOyM")
                .withMenu(R.menu.refresh_formats_data)
                .withMenuItems(getMenuItems(),getMenuClicks())
                .withBackButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                }).build();

        return builder;
    }
}
