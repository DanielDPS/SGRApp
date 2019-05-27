package gcode.baseproject.domain.repository.customer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import gcode.baseproject.interactors.MyContext;
import gcode.baseproject.interactors.db.dao.CustomerDao;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.progress.ValidatingProgress;
import gcode.baseproject.view.ui.account.MainActivity;

public class GetDataAsyncTask  extends AsyncTask<Void,Void, List<CustomerEntity>>
{

    private CustomerDao customerDaoAsyncTask ;
     private List<CustomerEntity> list;
     public  GetDataAsyncTask(CustomerDao DAO ){
        this.customerDaoAsyncTask= DAO;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    public List<CustomerEntity> getList(){
        return this.list;
    }
    @Override
    protected List<CustomerEntity> doInBackground(Void... voids) {
        this.list= customerDaoAsyncTask.getCustomers();
        return list;
    }
    @Override
    protected void onPostExecute(List<CustomerEntity> entities) {
        super.onPostExecute(entities);

    }
}
