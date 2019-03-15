package gcode.baseproject.domain.repository.customer;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.interactors.db.dao.CustomerDao;
import gcode.baseproject.interactors.db.entities.CustomerEntity;

public class GetDataAsyncTask  extends AsyncTask<Void,Void, List<CustomerEntity>>
{

    private CustomerDao customerDaoAsyncTask ;
    private List<CustomerEntity> list;
    public  GetDataAsyncTask(CustomerDao DAO){
        this.customerDaoAsyncTask= DAO;

    }
    public List<CustomerEntity> getList(){
        return this.list;
    }
    @Override
    protected List<CustomerEntity> doInBackground(Void... voids) {
        this.list= customerDaoAsyncTask.getCustomers();
        return list;
    }
}
