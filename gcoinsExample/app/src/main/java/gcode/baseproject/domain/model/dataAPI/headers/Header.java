package gcode.baseproject.domain.model.dataAPI.headers;

import com.google.gson.annotations.SerializedName;

public class Header  {


    @SerializedName("cid")
    private  String Id;
    @SerializedName("cidformato")
    private String FkFormat;
    @SerializedName("cusuariocre")
    private  String UserName;
    @SerializedName("cmodificacion")
    private  String Edited;
    @SerializedName("cidentificador")
    private  String Identifier;
    @SerializedName("cinicio")
    private String InitialDate;
    @SerializedName("cfin")
    private String EndDate;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFkFormat() {
        return FkFormat;
    }

    public void setFkFormat(String fkFormat) {
        FkFormat = fkFormat;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEdited() {
        return Edited;
    }

    public void setEdited(String edited) {
        Edited = edited;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    public String getInitialDate() {
        return InitialDate;
    }

    public void setInitialDate(String initialDate) {
        InitialDate = initialDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}
