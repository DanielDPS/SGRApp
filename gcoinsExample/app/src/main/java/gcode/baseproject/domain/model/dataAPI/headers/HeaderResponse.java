package gcode.baseproject.domain.model.dataAPI.headers;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeaderResponse {

    @SerializedName("status")
    private boolean Status;

    public boolean isStatus() {
        return Status;
    }


    @SerializedName("result")
    List<Header> listHeaders;

    public List<Header> getListHeaders() {
        return listHeaders;
    }

    public void setListHeaders(List<Header> listHeaders) {
        this.listHeaders = listHeaders;
    }
}
