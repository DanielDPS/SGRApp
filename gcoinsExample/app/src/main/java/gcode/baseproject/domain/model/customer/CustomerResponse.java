package gcode.baseproject.domain.model.customer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class CustomerResponse {

    @SerializedName("status")
    private  boolean Success;
    @SerializedName("message")
    private  String Message;

    @SerializedName("result")
    private List<Customer> customer;

    public void setSuccess(boolean response){this.Success=response;}
    public boolean isSuccessFull(){return Success;}

    public void setMessage(String message){Message = message;}
    public String getMessage(){return Message;}

    public void setClient(List<Customer> client){customer= client;}
    public  List<Customer> getClient(){return customer;}
}
