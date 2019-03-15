package gcode.baseproject.domain.model.questionGroup;

import com.google.gson.annotations.SerializedName;

public class QuestionGroup {
    @SerializedName("cid")
    private  String Id;
    @SerializedName("cdescripcion")
    private  String Description;
    @SerializedName("ctieneref")
    private  boolean Reference;

    public  void setId(String id){
        Id=id;
    }
    public  String getId(){
        return Id;
    }
    public  void setDescription(String description){
        Description= description;
    }
    public  String getDescription(){
        return Description;
    }
    public  void setReference(boolean reference){
        Reference= reference;
    }
    public boolean getReference(){
        return Reference;
    }
}
