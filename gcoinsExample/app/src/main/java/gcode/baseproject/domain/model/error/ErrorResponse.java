package gcode.baseproject.domain.model.error;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("message")
    private String mMessage;

    public void setMessage (String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }
}
