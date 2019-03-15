package gcode.baseproject.domain.repository.customer;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.CustomerDao;

public class GetCustomerIdAsyncTask extends AsyncTask<Void,Void,String> {

    private CustomerDao customerDaoAT;
    private  String customerId;
    private  String name;
    public  GetCustomerIdAsyncTask(CustomerDao dao,String name)
    {
        this.customerDaoAT = dao;
        this.name = name;
    }
    public  String getCustomerId(){
        return this.customerId;
    }
    @Override
    protected String doInBackground(Void... voids) {
        customerId=this.customerDaoAT.getCustomerIdByName(this.name);
        return customerId;
    }
}
