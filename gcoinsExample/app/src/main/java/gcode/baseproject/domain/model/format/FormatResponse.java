package gcode.baseproject.domain.model.format;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import gcode.baseproject.domain.model.formatSection.FormatSection;

public class FormatResponse {

    @SerializedName("status")
    private  boolean Status;
    @SerializedName("message")
    private  String Message;
    @SerializedName("result")
    private List<Format> formats;

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

    public List<Format> getFormats() {
        return formats;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

}
