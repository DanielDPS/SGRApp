package gcode.baseproject.domain.repository.customer;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import gcode.baseproject.domain.model.customer.Customer;
import gcode.baseproject.domain.model.customer.CustomerIdentifierResponse;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface ICustomerRepository {

    void AddCustomerDB(CustomerEntity customerEntity);
    void UpdateCustomerDB(CustomerEntity customerEntity);
    Completable DeleteCustomerDB(CustomerEntity customerEntity);
    List<CustomerEntity>  getCustomersDB( );
    Single<List<Customer>> getCustomers(String token, String lastUpdate);
    Boolean checkIfCustomersExists(String id);
    Boolean checkIfCustomersUpdate(String id,String lastUpdate);
    String getCustomerLastUpdate();
    Single<List<String>> getCustomerNamesDB();
    String getIdCustomerByName(String name);
    Single<String> getIdentifier(String token,String id);
    CustomerEntity getCustomerByID(String id);
    CustomerIdentifierResponse GetIdentifierAsync(String token,String idCustomer);
}
