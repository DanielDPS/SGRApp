package gcode.baseproject.domain.model.data;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class FormatData {

    @SerializedName("cid")
    private String Id;
    @SerializedName("cidformato")
    private String FkFormat;
    @SerializedName("cidcliente")
    private  String FkCustomer;
    @SerializedName("ccreacion")
    private DateTime  Creation;
    @SerializedName("cmodificacion")
    private  DateTime Upgrade;
    @SerializedName("cidusuariocre")
    private String FkCreationUser;
    @SerializedName("cidusuariomod")
    private String FkUpdateUser;

    public  void  setId(String id){
        Id=id;
    }
    public  String getId(){
        return Id;
    }
    public void setFkFormat(String fkformat){
        FkFormat=fkformat;
    }
    public   String getFkFormat(){
        return  FkFormat;
    }
    public  void setFkCustomer(String fkcustomer){
        FkCustomer =fkcustomer;
    }
    public  String getFkCustomer(){
        return  FkCustomer;
    }
    public void setCreation(DateTime creation){
        Creation =creation;
    }
    public  DateTime getCreation(){
        return Creation;
    }
    public  void setUpgrade(DateTime upgrade){
        Upgrade =upgrade;
    }
    public  DateTime getUpgrade(){
        return Upgrade;
    }
    public  void setFkCreationUser(String fkcreactionuser){
        FkCreationUser = fkcreactionuser;
    }
    public String getFkCreationUser(){
        return FkCreationUser;
    }
    public  void setFkUpdateUser(String fkupdateuser){
        FkUpdateUser = fkupdateuser;
    }
    public String getFkUpdateUser(){
        return FkUpdateUser;
    }


}
