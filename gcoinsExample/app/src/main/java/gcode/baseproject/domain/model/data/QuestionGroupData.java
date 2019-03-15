package gcode.baseproject.domain.model.data;

import com.google.gson.annotations.SerializedName;

public class QuestionGroupData {

    @SerializedName("cid")
    private  String Id;
    @SerializedName("cidgrupopre")
    private  String FkQuestionGroup;
    @SerializedName("creferencia")
    private  String Reference;


    public void setId(String id){
        Id=id;
    }
    public String getId(){
        return Id;
    }
    public void setFkQuestionGroup(String fkquestiongroup){
        FkQuestionGroup=fkquestiongroup;
    }
    public  String  getFkQuestionGroup(){
        return FkQuestionGroup;
    }
    public void setReference(String reference){
        Reference= reference;
    }
    public   String getReference(){
        return Reference;
    }

}
