package gcode.baseproject.domain.model.customer;

import com.google.gson.annotations.SerializedName;

public class CustomerIdentifierResponse {


    @SerializedName("status")
    private  boolean Success;
    @SerializedName("message")
    private  String Message;
    @SerializedName("result")
    private  String Identifier;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }
}
