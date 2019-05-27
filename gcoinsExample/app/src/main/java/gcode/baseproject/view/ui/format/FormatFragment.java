package gcode.baseproject.view.ui.format;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatsFragmentBinding;
import gcode.baseproject.interactors.adapters.FormatAdapter;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.viewmodel.dataAPI.file.FileViewModel;
import gcode.baseproject.view.viewmodel.dataAPI.fileHeaders.FileHeaderViewModel;
import gcode.baseproject.view.viewmodel.dataAPI.headers.HeaderViewModel;
import gcode.baseproject.view.viewmodel.format.FormatViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class FormatFragment extends BaseFragment  implements FormatAdapter.FormatPresenter{
    private FormatsFragmentBinding formatsFragmentBinding ;
    private FormatViewModel formatViewModel;
    private  FormatAdapter formatAdapter;
    private ProgressDialog dialog;

    //VIEWMODELS
    private HeaderViewModel headerViewModel;
    private FileHeaderViewModel fileHeaderViewModel;
    private FileViewModel fileViewModel;
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
        headerViewModel= ViewModelProviders.of(this).get(HeaderViewModel.class);
        fileHeaderViewModel =ViewModelProviders.of(this).get(FileHeaderViewModel.class);
        fileViewModel = ViewModelProviders.of(this).get(FileViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        formatsFragmentBinding = FormatsFragmentBinding.inflate(inflater,container,false);
        return formatsFragmentBinding.getRoot();
    }

       void Refresh(){
           formatsFragmentBinding.formatsRecycler.setHasFixedSize(true);
          formatsFragmentBinding.formatsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
          formatAdapter = new FormatAdapter(formatViewModel.getFormatsDB(),this);
          formatsFragmentBinding.formatsRecycler.setAdapter(formatAdapter);
       }

    private  void SyncFormats(){
           dialog = new ProgressDialog(getContext(), AlertDialog.THEME_HOLO_DARK);
           dialog.setMessage("Sincronizando formatos...");
           dialog.setIndeterminate(false);
           dialog.setCancelable(false);
           dialog.show();
           formatViewModel.UpdateFormats()
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new DisposableCompletableObserver() {
                       @Override
                       public void onComplete() {
                           dialog.dismiss();
                           Refresh();
                       }

                       @Override
                       public void onError(Throwable e) {
                           dialog.dismiss();
                       }
                   });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (formatViewModel.getFormatsDB().size() >0){
            Refresh();
        }else {
            SyncFormats();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /** METODOS GET API**/
    //GET DATOS FORMATOS
    //GET HEADERS
                  /*
                  headerViewModel.ClearHeaders(contextFragment);
                  headerViewModel.getHeadersJSON("","d366038f9183692577899e653203bd96");
                  headerViewModel.mGetLiveHeaders().observe(contextFragment, new Observer<List<Header>>() {
                      @Override
                      public void onChanged(List<Header> headers) {

                          for (Header obj : headers){
                              Toast.makeText(getContext(),obj.getId(),Toast.LENGTH_LONG).show();
                          }

                      }
                  });*/


                  /*
                  //GET FILE HEADERS
                  fileHeaderViewModel.ClearFileHeaders(contextFragment);
                  fileHeaderViewModel.LoadFileHeaders("c81e728d9d4c2f636f067f89cc14862c");
                  fileHeaderViewModel.mGetLiveFileHeaders().observe(contextFragment, new Observer<List<FileHeader>>() {
                      @Override
                      public void onChanged(List<FileHeader> fileHeaders) {

                               Toast.makeText(getContext(),String.valueOf(fileHeaders.size()),Toast.LENGTH_LONG).show();

                      }
                  });*/


    //GET FILE

                  /*
                  fileViewModel.ClearEncodedPath(contextFragment);
                  fileViewModel.LoadEncodedPath("0cc175b9c0f1b6a831c399e269772661");
                  fileViewModel.mGetFile().observe(contextFragment, new Observer<String>() {
                      @Override
                      public void onChanged(String encodedPath) {
                          Toast.makeText(getContext(),encodedPath,Toast.LENGTH_LONG).show();
                      }
                  });*/
    /** METODOS GET API**/
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
           final FormatFragment contextFragment = this;
          List<MenuItem.OnMenuItemClickListener>listeners  = new ArrayList<>();
          listeners.add(new MenuItem.OnMenuItemClickListener() {
              @Override
              public boolean onMenuItemClick(MenuItem item) {
                  ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                  NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                  if (networkInfo!=null && networkInfo.isConnected()){
                      SyncFormats();
                  }else {
                      Toast.makeText(getContext(), "Sin conexión a internet, verifique su conexión", Toast.LENGTH_SHORT).show();
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
