package gcode.baseproject.domain.model.module;

import com.google.gson.annotations.SerializedName;

public class Module {

    @SerializedName("cid")
    private  String Id;
    @SerializedName("cnombre")
    private  String Name;
    @SerializedName("calias")
    private  String Alias;
    @SerializedName("cicono")
    private  String Icon;
    @SerializedName("cidmodulopad")
    private  String FkModulePad;
    @SerializedName("cmodulo")
    private boolean Modulo;
    @SerializedName("corden")
    private  Integer Order;
    @SerializedName("cweb")
    private boolean Web;
    @SerializedName("capp")
    private  boolean App;

    public void setId(String id){
        Id=id;
    }
    public String getId(){
        return Id;
    }
    public  void setNombre(String nombre){
        Name=nombre;
    }
    public String getNombre(){
        return Name;
    }
    public void setAlias(String alias){
        Alias=alias;
    }
    public  String getAlias(){
        return Alias;
    }
    public void setIcono(String icon){
        Icon=icon;
    }

    public String getIcono(){
        return Icon;
    }
    public void setFkModuloPad(String fkmodulepad){
        FkModulePad=fkmodulepad;
    }
    public  String getFkModuloPad(){
        return FkModulePad;
    }
    public void setModule(boolean modulo){
        Modulo= modulo;
    }
    public boolean getModule(){
        return Modulo;
    }
    public void setOrder(Integer order){
        Order=order;
    }
    public Integer getOrder(){
        return Order;
    }
    public void setWeb(boolean web){
        Web =web;
    }
    public boolean getWeb(){
        return Web;
    }
    public void setApp(boolean app){
        App=app;
    }
    public boolean getApp(){
        return  App;
    }




}
