package gcode.baseproject.domain.repository.customer;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.CustomerDao;
import gcode.baseproject.interactors.db.entities.CustomerEntity;

public class GetCustomerByIdAsyncTask extends AsyncTask<Void,Void, CustomerEntity> {


    private CustomerDao customerDao;
    private  CustomerEntity customerEntity;
    private  String id ;
    public GetCustomerByIdAsyncTask(CustomerDao dao,String id ){
        this.customerDao =dao;
        this.id = id;
    }
    public CustomerEntity getCustomerEntity(){
        return this.customerEntity;
    }
    @Override
    protected CustomerEntity doInBackground(Void... voids) {
        this.customerEntity = this.customerDao.getCustomerById(id);
        return this.customerEntity;
    }
}
