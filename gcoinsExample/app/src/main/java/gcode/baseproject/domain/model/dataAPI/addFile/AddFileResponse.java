package gcode.baseproject.domain.model.dataAPI.addFile;

import com.google.gson.annotations.SerializedName;

public class AddFileResponse {

    @SerializedName("status")
    private  boolean Status;
    @SerializedName("message")
    private  String EncodedPathMessage;

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getEncodedPathMessage() {
        return EncodedPathMessage;
    }

    public void setEncodedPathMessage(String encodedPathMessage) {
        EncodedPathMessage = encodedPathMessage;
    }
}
