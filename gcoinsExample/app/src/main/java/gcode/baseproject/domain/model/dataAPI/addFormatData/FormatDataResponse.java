package gcode.baseproject.domain.model.dataAPI.addFormatData;

import com.google.gson.annotations.SerializedName;

public class FormatDataResponse {

    @SerializedName("status")
    private  boolean Status;
    @SerializedName("message")
    private String Message;

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
