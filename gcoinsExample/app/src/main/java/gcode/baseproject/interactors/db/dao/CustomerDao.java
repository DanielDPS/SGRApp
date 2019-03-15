package gcode.baseproject.interactors.db.dao;

 import java.util.List;
 import java.util.concurrent.Executor;
 import java.util.concurrent.Executors;

 import androidx.lifecycle.LiveData;
 import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
 import androidx.room.Query;
import androidx.room.Update;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import io.reactivex.Single;

@Dao
public interface CustomerDao {

    @Insert
    void insert(CustomerEntity customerEntity);

    @Update
    void updateCustomer(CustomerEntity customerEntity);

    @Delete
    void removeCustomer(CustomerEntity customerEntity);

    @Query("SELECT * FROM tblcustomer")
    List<CustomerEntity> getCustomers();

    @Query("SELECT COUNT(*) FROM tblcustomer WHERE id = :id")
    Integer getCountCustomers(String id);

    @Query("SELECT edited FROM tblcustomer WHERE edited = (SELECT MAX (edited) FROM  tblcustomer) LIMIT 1")
    String getMaxLastUpdate();

    @Query("SELECT COUNT(*) FROM  tblcustomer WHERE id= :id AND edited < :lastUpdate")
    Integer checkIfCustomersShouldUpdate(String id,String lastUpdate);

    @Query("SELECT businessname FROM tblcustomer ORDER BY businessname ASC")
    Single<List<String>> getCustomerNames();

    @Query("SELECT id FROM tblcustomer WHERE businessname= :name")
    String getCustomerIdByName(String name);

    @Query("SELECT * FROM tblcustomer WHERE id =:idcustomer")
    CustomerEntity getCustomerById(String idcustomer);



}
