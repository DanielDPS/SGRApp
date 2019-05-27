package gcode.baseproject.view.ui.Customer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
 import gcode.baseproject.R;
 import gcode.baseproject.databinding.CustomersFragmentBinding;
import gcode.baseproject.interactors.MyContext;
import gcode.baseproject.interactors.adapters.CustomerAdapter;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.progress.ValidatingProgress;
import gcode.baseproject.view.ui.account.LoginActivity;
import gcode.baseproject.view.ui.account.MainActivity;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.ui.general.ExitDialogFragment;
import gcode.baseproject.view.viewmodel.Customer.CustomerViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
 import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;

public class CustomerFragment extends BaseFragment implements CustomerAdapter.CustomersPresenter {

    private CustomersFragmentBinding mCustomerBinding;
    private  CustomerAdapter customerAdapter;
    private  List<CustomerEntity >tempentities;
    private  CustomerFragment fragment = this;
    private ValidatingProgress validatingProgress;
    private ProgressDialog dialog;
      public  static CustomerFragment getInstance() {

          return new CustomerFragment();
      }
    private CustomerViewModel customerViewModel;

      @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);
          customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);

      }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        mCustomerBinding = CustomersFragmentBinding.inflate(inflater,parent,false);
        return mCustomerBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    void Refresh(){
          if (customerViewModel.listCustomersDB().size() ==0){
              mCustomerBinding.nodata.setVisibility(View.VISIBLE);

          }else {
              mCustomerBinding.nodata.setVisibility(View.GONE);
              mCustomerBinding.customersRecycler.setHasFixedSize(true);
              mCustomerBinding.customersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
              mCustomerBinding.customersRecycler.setAdapter(new CustomerAdapter(customerViewModel.listCustomersDB(),this,fragment));
          }


    }

    private  void SyncCustomers(){
          dialog = new ProgressDialog(getContext(), AlertDialog.THEME_HOLO_DARK);
          dialog.setMessage("Sincronizando clientes...");
          dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          dialog.setIndeterminate(false);
          dialog.setCancelable(false);
          dialog.show();
          customerViewModel.UpdateData()
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
        if (customerViewModel.listCustomersDB().size() >0){
            Refresh();
        }
        else {
            SyncCustomers();
        }
    }
    @NonNull
    @Override
    public String getFragmentTag() {
        return CustomerFragment.class.getName();
    }

    private List<Integer> menuItems(){
        List<Integer> menuItems = new ArrayList<>();
        menuItems.add(R.id.Customers_refresh);
        return menuItems;
    }

    private  List<MenuItem.OnMenuItemClickListener> menuClicks(){

        List<MenuItem.OnMenuItemClickListener> listeners=new ArrayList<>();
        listeners.add(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ConnectivityManager connectivityManager =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo !=null && networkInfo.isConnected()){
                    SyncCustomers();
                }else {
                    Toast.makeText(getContext(), "Sin conexión a internet, verifique su conexión", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        return listeners;
     }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
          ToolbarBuilder builder = new ToolbarBuilder
            .Builder(mCustomerBinding.toolbar)
                .withMenu(R.menu.refresh_customers)
                .withMenuItems(menuItems(),menuClicks())
            .withTitle("Clientes")
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
    public void OnClickPresenter(CustomerEntity customerEntity) {
        getNavigationManager().addFragment(CustomersDetailFragment.newInstance(customerEntity));
    }
}
