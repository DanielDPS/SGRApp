package gcode.baseproject.domain.model.option;

import com.google.gson.annotations.SerializedName;

public class Option {

    @SerializedName("cid")
    private  String Id;
    @SerializedName("cdescripcion")
    private  String Description;

    public void setId(String id){
        Id=id;
    }
    public  String getId(){
        return Id;
    }
    public  void setDescription(String description){
        Description =description;
    }
    public  String getDescription(){
        return Description;
    }

}
