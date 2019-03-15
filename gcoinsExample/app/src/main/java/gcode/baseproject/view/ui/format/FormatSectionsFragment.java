package gcode.baseproject.view.ui.format;

import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatSectionsFragmentBinding;
import gcode.baseproject.interactors.adapters.FormatSectionsAdapter;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.progress.ValidatingProgress;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.viewmodel.format.FormatViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;

public class FormatSectionsFragment extends BaseFragment implements  FormatSectionsAdapter.FormatSectionPresenter {

    private  static String FORMAT_DATA_ARGS ="format_data_args";
    private FormatSectionsFragmentBinding formatSectionsFragmentBinding;
    private FormatDataEntity format;
    private  FormatViewModel formatViewModel;
    private  Integer total =0;
     public static final FormatSectionsFragment newInstance(FormatDataEntity format){
        FormatSectionsFragment formatSectionsFragment = new FormatSectionsFragment();
        Bundle args= new Bundle();
        args.putSerializable(FORMAT_DATA_ARGS,format);
        formatSectionsFragment.setArguments(args);
        return formatSectionsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            format = (FormatDataEntity)getArguments().getSerializable(FORMAT_DATA_ARGS);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        formatViewModel = ViewModelProviders.of(this).get(FormatViewModel.class);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        formatSectionsFragmentBinding = FormatSectionsFragmentBinding.inflate(inflater,container,false);
        return formatSectionsFragmentBinding.getRoot();
    }

    private Observer<List<FormatSectionEntity>> getObserver(){
         final FormatSectionsAdapter.FormatSectionPresenter presenter= this;
         final FormatSectionsFragment fragment = this;
        return new Observer<List<FormatSectionEntity>>() {
            @Override
            public void onChanged(List<FormatSectionEntity> formatSectionEntities) {

                     //total = formatViewModel.getQuestionsCountByIdSection(formatSectionEntity.getId()).;


                formatSectionsFragmentBinding.formatsSectionsRecycler.setHasFixedSize(true);
                formatSectionsFragmentBinding.formatsSectionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                FormatSectionsAdapter formatSectionsAdapter= new FormatSectionsAdapter(formatSectionEntities, presenter,fragment);
                formatSectionsFragmentBinding.formatsSectionsRecycler.setAdapter(formatSectionsAdapter);


            }
        };
    }

    void LoadFormatSections(){
        formatViewModel.LoadSections(format.getFkformat());
        formatViewModel.mgetFormatSections()
                .observe(this,getObserver());
     }
    @Override
    public void onResume() {
        super.onResume();
        LoadFormatSections();
     }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @NonNull
    @Override
    public String getFragmentTag() {
        return FormatSectionsFragment.class.getName();
    }

    private  List<Integer> getMenuItems(){
         List<Integer> menuItems = new ArrayList<>();
         menuItems.add(R.id.Section_save);
         return  menuItems;
    }
    private  List<MenuItem.OnMenuItemClickListener>getMenuClicks(){
         List<MenuItem.OnMenuItemClickListener>listeners= new ArrayList<>();
         listeners.add(new MenuItem.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem item) {
                 Toast.makeText(getContext(),"Guardar Inspeccion",Toast.LENGTH_SHORT).show();
                 return false;
             }
         });
         return listeners;
    }
    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(formatSectionsFragmentBinding.toolbar)
                .withTitle("Secciones")
                .withMenu(R.menu.sections_menu)
                .withMenuItems(getMenuItems(),getMenuClicks())
                .withBackButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                })
                .build();
        return builder;
    }


    @Override
    public void OnClickSectionPresenter(FormatSectionEntity formatSectionEntity) {
         getNavigationManager().addFragment(FormFragment.newInstance(formatSectionEntity,format));
    }
}
