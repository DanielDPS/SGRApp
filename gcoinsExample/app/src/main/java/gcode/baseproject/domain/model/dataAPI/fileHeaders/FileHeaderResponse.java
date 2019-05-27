package gcode.baseproject.domain.model.dataAPI.fileHeaders;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FileHeaderResponse {

    @SerializedName("result")
    private List<FileHeader> listFileHeaders;

    public List<FileHeader> getListFileHeaders() {
        return listFileHeaders;
    }

    public void setListFileHeaders(List<FileHeader> listFileHeaders) {
        this.listFileHeaders = listFileHeaders;
    }
}
