package gcode.baseproject.domain.repository.customer;

import android.os.AsyncTask;

import gcode.baseproject.domain.model.customer.CustomerIdentifierResponse;
import gcode.baseproject.interactors.api.customer.CustomerService;

public class GetIdentifier extends AsyncTask<Void,Void, CustomerIdentifierResponse> {

    private CustomerService service;
    private  CustomerIdentifierResponse identifier;
    private  String idCustomer;
    private  String token;

    public  GetIdentifier(CustomerService service,String token ,String idcustomer){
        this.service = service;
        this.token= token;
        this.idCustomer = idcustomer;
    }
    public  CustomerIdentifierResponse getIdentifier(){
        return  identifier;
    }


    @Override
    protected CustomerIdentifierResponse doInBackground(Void... voids) {
        return identifier =service.getCustomerIdentifierAsync(token,idCustomer);
    }
}
