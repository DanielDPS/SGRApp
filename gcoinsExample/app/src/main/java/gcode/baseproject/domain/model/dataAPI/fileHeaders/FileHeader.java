package gcode.baseproject.domain.model.dataAPI.fileHeaders;

import com.google.gson.annotations.SerializedName;

public class FileHeader {

    @SerializedName("cid")
    private String Id;
    @SerializedName("ctipo")
    private String FileType;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFileType() {
        return FileType;
    }

    public void setFileType(String fileType) {
        FileType = fileType;
    }
}
