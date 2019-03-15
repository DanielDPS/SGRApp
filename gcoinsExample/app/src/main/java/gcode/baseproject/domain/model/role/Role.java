package gcode.baseproject.domain.model.role;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.Date;

public class Role {

    @SerializedName("cid")
    private  String Id;
    @SerializedName("cnombre")
    private String Name;
    @SerializedName("ccreacion")
    private DateTime Creation;
    @SerializedName("cmodificacion")
    private DateTime Upgrade;
    @SerializedName("cactivo")
    private  boolean Active;

    public  void setId(String id){
        Id=id;
    }
    public  String getId(){
        return Id;
    }
    public  void setName(String name){
        Name=name;
    }
    public  String getName(){
        return Name;
    }
    public  void setCreation(DateTime creation){
        Creation = creation;
    }
    public  DateTime getCreation(){
        return Creation;
    }
    public  void setUpgrade(DateTime upgrade){
        Upgrade = upgrade;
    }
    public  DateTime getUpgrade(){
        return Upgrade;
    }
    public  void setActive(boolean active){
        Active =active;
    }
    public  boolean getActive(){
        return Active;
    }



}
