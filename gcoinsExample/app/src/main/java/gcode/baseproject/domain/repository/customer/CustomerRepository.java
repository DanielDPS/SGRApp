package gcode.baseproject.domain.repository.customer;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.util.Log;


 import java.util.List;
import java.util.concurrent.ExecutionException;

import gcode.baseproject.domain.model.customer.Customer;
import gcode.baseproject.domain.model.customer.CustomerIdentifierResponse;
import gcode.baseproject.domain.model.customer.CustomerResponse;
 import gcode.baseproject.interactors.api.NetworkManager;
import gcode.baseproject.interactors.api.customer.CustomerService;
import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.CustomerDao;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import io.reactivex.Completable;
import io.reactivex.functions.Function;
import io.reactivex.Single;

public class CustomerRepository implements  ICustomerRepository {


    private CustomerService customerService;
    private CustomerDao customerDao;
     public  CustomerRepository( ){
        customerService = NetworkManager.getInstance().create(CustomerService.class);
        customerDao = ApplicationDatabase.getDatabase().getCustomerDao();

     }


    @Override
    public void AddCustomerDB(final CustomerEntity customerEntity) {

                customerDao.insert(customerEntity);
    }

    @Override
    public void UpdateCustomerDB(final CustomerEntity customerEntity) {

                customerDao.updateCustomer(customerEntity);

    }

    @Override
    public Completable DeleteCustomerDB(final CustomerEntity customerEntity) {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                customerDao.removeCustomer(customerEntity);
            }
        });
    }

    @Override
    public List<CustomerEntity> getCustomersDB()   {
        GetDataAsyncTask task  = new GetDataAsyncTask(customerDao);
        try {
             task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return task.getList();
    }

    @Override
    public Single<List<Customer>> getCustomers(String token, String lastUpdate) {
        return customerService.getCustomers(token, lastUpdate)
                .map(new Function<CustomerResponse, List<Customer>>() {
                    @Override
                    public List<Customer> apply(CustomerResponse customerResponse) throws Exception {
                        return customerResponse.getClient();
                    }
                });
    }

    @Override
    public Boolean checkIfCustomersExists(String id) {
        return customerDao.getCountCustomers(id) >0;
    }

    @Override
    public Boolean checkIfCustomersUpdate(String id, String lastUpdate) {
        return customerDao.checkIfCustomersShouldUpdate(id,lastUpdate)> 0;
    }

    @Override
    public String getCustomerLastUpdate() {
        String lastUpdate = null;
        try {

            lastUpdate = customerDao.getMaxLastUpdate();
        }
        catch (Exception e) {
            Log.e("TAG1", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return lastUpdate ==null ? "" :lastUpdate;
    }

    @Override
    public Single<List<String>> getCustomerNamesDB() {
        return customerDao.getCustomerNames();
    }

    @Override
    public String getIdCustomerByName(String name) {
        GetCustomerIdAsyncTask task = new GetCustomerIdAsyncTask(customerDao,name);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getCustomerId();
    }

    @Override
    public Single<String> getIdentifier(String token, String id) {


        return customerService.getCustomerIdentifier(token,id)
                .map(new Function<CustomerIdentifierResponse, String>() {
                    @Override
                    public String apply(CustomerIdentifierResponse customerIdentifierResponse) throws Exception {
                        return customerIdentifierResponse.getIdentifier();
                    }
                });
    }

    @Override
    public CustomerEntity getCustomerByID(String id) {
        GetCustomerByIdAsyncTask task = new GetCustomerByIdAsyncTask(customerDao,id);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getCustomerEntity();

    }

    @Override
    public CustomerIdentifierResponse GetIdentifierAsync(String token, String idCustomer) {
         GetIdentifier task = new GetIdentifier(customerService,token,idCustomer);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getIdentifier();
    }


}
