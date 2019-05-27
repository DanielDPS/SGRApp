package gcode.baseproject.domain.model.dataAPI.file;

import com.google.gson.annotations.SerializedName;

public class FileResponse {

    @SerializedName("result")
    private  String EncodedPath;

    public String getEncodedPath() {
        return EncodedPath;
    }

    public void setEncodedPath(String encodedPath) {
        EncodedPath = encodedPath;
    }
}
