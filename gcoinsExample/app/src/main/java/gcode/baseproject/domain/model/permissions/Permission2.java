package gcode.baseproject.domain.model.permissions;

import com.google.gson.annotations.SerializedName;

public class Permission2 {

    @SerializedName("cidaccion")
    private  String FkAccion;
    @SerializedName("cidrol")
    private  String FkRol;

    public  void setFkAccion(String fkaccion){
        FkAccion=fkaccion;
    }
    public String getFkAccion(){
        return FkAccion;
    }
    public  void setFkRol(String fkrol){
        FkRol = fkrol;
    }
    public  String getFkRol(){
        return FkRol;
    }
}
