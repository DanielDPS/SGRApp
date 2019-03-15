package gcode.baseproject.domain.model.action;

import com.google.gson.annotations.SerializedName;

public class Action {

    @SerializedName("cid")
    private  String Id;
    @SerializedName("cidmodulo")
    private  String FkModule;
    @SerializedName("caccion")
    private String Action;
    @SerializedName("cidentificador")
    private char Identifier;

    public  void  setId(String id){
        Id=id;
    }
    public  String getId(){
        return Id;
    }
    public  void setFkModule(String fkmodule){
        FkModule=fkmodule;
    }
    public  String getFkModule(){
        return FkModule;
    }
    public  void setAction(String action){
        Action = action;
    }
    public  String getAction(){
        return Action;
    }
    public  void setIdentifier(char identifier){
        Identifier= identifier;
    }
    public  char getIdentifier(){
        return Identifier;
    }
}
