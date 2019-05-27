package gcode.baseproject.view.viewmodel.Customer;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.lang.reflect.Array;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatDataModuleFragmentBinding;
import gcode.baseproject.domain.model.customer.Customer;
import gcode.baseproject.domain.model.customer.CustomerIdentifierResponse;
import gcode.baseproject.domain.repository.customer.CustomerRepository;
import gcode.baseproject.domain.repository.customer.ICustomerRepository;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.view.utils.ErrorUtils;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.completable.CompletableFromUnsafeSource;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;

public  class CustomerViewModel extends BaseNetworkViewModel {

    private   MutableLiveData<List<CustomerEntity>> mCustomersEntity = new MutableLiveData<>();
    private   MutableLiveData<List<String>> mCustomerNames = new MutableLiveData<>();
    private   MutableLiveData<String> getIdentifier  = new MutableLiveData<>();
     private ICustomerRepository iCustomerRepository;
     private   String update;

      public CustomerViewModel(@NonNull Application application){
        super(application);
        iCustomerRepository= new CustomerRepository();
    }
    public  CustomerEntity getCustomerEntityById(String id){
          return iCustomerRepository.getCustomerByID(id);
    }
    public String getCustomerId(String name){
          return iCustomerRepository.getIdCustomerByName(name);
    }

    private  Single<List<Customer>> getCustomersFromAPI() {
        Single<List<Customer>> customersJSON= getSessionManager()
                .getToken()
                .flatMap(new Function<String,Single<List<Customer>>>(){
                    @Override
                    public Single<List<Customer>> apply(String token) {
                        update =iCustomerRepository.getCustomerLastUpdate();
                        Log.e("TAG4",update);
                         return iCustomerRepository.getCustomers(token, update);
                    }
                });
        return customersJSON;
    }



    public List<CustomerEntity> listCustomersDB() {
            return iCustomerRepository.getCustomersDB();
    }

    public  Completable UpdateData() {

            Single<List<Customer>> customersfromJSONwithLastUpdate = getCustomersFromAPI();



            Completable addCustomersToDB = customersfromJSONwithLastUpdate.flatMapCompletable(new Function<List<Customer>, CompletableSource>() {
                @Override
                public CompletableSource apply(final List<Customer> customers) throws Exception {
                    return Completable.fromRunnable(new Runnable() {
                        @Override
                        public void run() {


                            for (Customer customer : customers){

                                CustomerEntity customerEntity = new CustomerEntity();
                                customerEntity.setCId(customer.getId());
                                customerEntity.setCIdentifier(customer.getIdentifier());
                                customerEntity.setCBusinessName(customer.getBusinessName());
                                customerEntity.setCActivity(customer.getActivity());
                                customerEntity.setCDirection(customer.getDirection());
                                customerEntity.setCEntity(customer.getEntity());
                                customerEntity.setCMail1(customer.getMail1());
                                customerEntity.setCMail2(customer.getMail2());
                                customerEntity.setCMail3(customer.getMail3());
                                customerEntity.setCNumber1(customer.getNumber1());
                                customerEntity.setCNumber2(customer.getNumber2());
                                customerEntity.setCInitialDate(customer.getInitialDate());
                                customerEntity.setCCreationPermission(customer.getCreationPermission());
                                customerEntity.setCRfc(customer.getRfc());
                                customerEntity.setCRepresentative(customer.getRepresentative());
                                customerEntity.setCCreation(customer.getCreation());
                                customerEntity.setCEdited(customer.getUpgrade());
                                customerEntity.setCActive(customer.getActive());

                                if (iCustomerRepository.checkIfCustomersExists(customerEntity.getCId())){

                                        if (iCustomerRepository.checkIfCustomersUpdate(customerEntity.getCId(),update)){
                                            iCustomerRepository.UpdateCustomerDB(customerEntity);
                                        }
                                }
                                iCustomerRepository.AddCustomerDB(customerEntity);
                            }
                        }
                    });
                }
            });


           return  addCustomersToDB;




    }
    public void LoadCustomerNames(){

          if (mCustomerNames.getValue() == null){
              Single<List<String>> names= iCustomerRepository.getCustomerNamesDB()
                      .map(new Function<List<String>, List<String>>() {
                          @Override
                          public List<String> apply(List<String> names) throws Exception {
                              return names;
                          }
                      });
              names.subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(getCustomerDBObserver());

          }

    }


    private DisposableSingleObserver<List<String>> getCustomerDBObserver() {
        DisposableSingleObserver<List<String>> observer = new DisposableSingleObserver<List<String>>() {
            @Override
            public void onSuccess(List<String> entities) {
                 mCustomerNames.setValue(entities);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    int statusCode = httpException.code();
                    switch (statusCode) {
                        case HttpURLConnection.HTTP_UNAUTHORIZED:
                            getSessionManager().onSessionDouble();
                            break;
                        case HttpURLConnection.HTTP_BAD_REQUEST:
                            String message = ErrorUtils.getErrorMessage(httpException.response().errorBody());
                            Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();

                            break;
                    }
                    String message = ErrorUtils.getErrorMessage(httpException.response().errorBody());
                    Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
                } else if (e instanceof UnknownHostException) {
                    Toast.makeText(
                            getApplication(),
                            R.string.socket_time_out_exception,
                            Toast.LENGTH_SHORT).show();
                } else if (e instanceof ConnectException) {
                    Toast.makeText(
                            getApplication(),
                            R.string.socket_time_out_exception,
                            Toast.LENGTH_SHORT).show();
                }

            }
        };
        return observer;

    }
    public MutableLiveData<List<String>> getmCustomerName(){
        return mCustomerNames;
    }

    public void ClearIdentifier(LifecycleOwner owner){
        if (getIdentifier != null){
            getIdentifier.removeObservers(owner);
            getIdentifier = null;
        }
    }
    public void LoadIdentifier(final String id){
          if (getIdentifier  == null){
              getIdentifier= new MutableLiveData<>();
          }
        Single<String> listIdentifiers = getSessionManager().getToken()
                .flatMap(
                        new Function<String, SingleSource<String>>() {
                            @Override
                            public SingleSource<String> apply(String token) throws Exception {
                                return iCustomerRepository.getIdentifier(token,id);
                            }
                        }
                );
        listIdentifiers.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSingleIdentifier());
}
    private  DisposableSingleObserver<String> getSingleIdentifier(){
          return new DisposableSingleObserver<String>() {
              @Override
              public void onSuccess(String identifier) {
                  getIdentifier.postValue(identifier);
              }

              @Override
              public void onError(Throwable e) {
                e.printStackTrace();
              }
          };
    }
    public MutableLiveData<String> getGetIdentifier() {
        return getIdentifier;
    }


    public void getIdentifierObject(final String idCustomer, final FormatDataModuleFragmentBinding binding){
          Single<String> token = getSessionManager().getToken()
                  .map(new Function<String, String>() {
                      @Override
                      public String apply(String token) throws Exception {
                          return token;
                      }
                  });
          Completable getIdentifier= token.flatMapCompletable(new Function<String, CompletableSource>() {
              @Override
              public CompletableSource apply(final String token) throws Exception {
                  return Completable.fromRunnable(new Runnable() {
                      @Override
                      public void run() {
                              iCustomerRepository.getIdentifier(token,idCustomer)
                                      .subscribeOn(Schedulers.io())
                                      .observeOn(AndroidSchedulers.mainThread())
                                      .subscribe(new SingleObserver<String>() {
                                          @Override
                                          public void onSubscribe(Disposable d) {
                                          }
                                          @Override
                                          public void onSuccess(String identifier) {
                                              binding.txtIdentifier.setText(identifier);
                                          }

                                          @Override
                                          public void onError(Throwable e) {
                                              e.printStackTrace();
                                              Log.e("TAG4",e.getLocalizedMessage());
                                          }
                                      });
                             //binding.txtIdentifier.setText(iCustomerRepository.GetIdentifierAsync(token,idCustomer).getIdentifier());
                      }
                  });
              }
          });
          getIdentifier.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe();
    }
}
