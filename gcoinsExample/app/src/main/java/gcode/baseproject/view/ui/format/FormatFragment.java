package gcode.baseproject.view.ui.format;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatsFragmentBinding;
import gcode.baseproject.domain.model.formatSection.FormatSection;
import gcode.baseproject.domain.model.question.Question;
import gcode.baseproject.interactors.adapters.FormatAdapter;
import gcode.baseproject.interactors.adapters.FormatSectionsAdapter;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.progress.ValidatingProgress;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.viewmodel.format.FormatViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;

public class FormatFragment extends BaseFragment  implements FormatAdapter.FormatPresenter{
    private FormatsFragmentBinding formatsFragmentBinding ;
    private FormatViewModel formatViewModel;
    private  FormatAdapter formatAdapter;
      public   static FormatFragment getInstance( ){
        return  new FormatFragment();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        formatViewModel = ViewModelProviders.of(this).get(FormatViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        formatsFragmentBinding = FormatsFragmentBinding.inflate(inflater,container,false);
        return formatsFragmentBinding.getRoot();
    }
    void RefreshAll(){
        ValidatingProgress validatingProgress = new ValidatingProgress(getContext(),"Formatos","Sincronizando....");
        validatingProgress.ShowProgressDialog();
        validatingProgress.Progress(formatsFragmentBinding.formatsRecycler,getContext(),new FormatAdapter(formatViewModel.getFormatsDB(),this));
      }
      void Refresh(){
          formatsFragmentBinding.formatsRecycler.setHasFixedSize(true);
          formatsFragmentBinding.formatsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
          formatAdapter = new FormatAdapter(formatViewModel.getFormatsDB(),this);
          formatsFragmentBinding.formatsRecycler.setAdapter(formatAdapter);
      }
    void RefreshFragment(){
         getActivity().getSupportFragmentManager().beginTransaction()
                 .detach(FormatFragment.getInstance())
                 .attach(new FormatFragment())
                 .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (formatViewModel.getFormatsDB().size() > 0){
            RefreshFragment();
            Refresh();

         }else {
            formatViewModel.UpdateFormats();
            RefreshFragment();
            Toast.makeText(getContext(),"No existen registros, favor de presionar el boton de sincronizacion",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @NonNull
    @Override
    public String getFragmentTag() {
        return FormatFragment.class.getName();
    }

    private  List<Integer> getMenuItems(){
          List<Integer> menuItems= new ArrayList<>();
          menuItems.add(R.id.Format_refresh);
          return menuItems;
    }
    private  List<MenuItem.OnMenuItemClickListener> getMenuClicks(){
          List<MenuItem.OnMenuItemClickListener>listeners  = new ArrayList<>();
          listeners.add(new MenuItem.OnMenuItemClickListener() {
              @Override
              public boolean onMenuItemClick(MenuItem item) {
                  if (formatViewModel.getFormatsDB().size() > 0){
                      formatViewModel.UpdateFormats();
                      RefreshAll();

                  }else {
                      RefreshFragment();
                      Refresh();
                  }
                  return false;
              }
          });
          return  listeners;
    }
    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(formatsFragmentBinding.toolbar)
                .withTitle("Formatos")
                .withMenu(R.menu.refresh_formats)
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
    public void onClickFormat(FormatEntity format) {
        getNavigationManager().addFragment(FormatDataFragment.newInstance(format));

    }
}
