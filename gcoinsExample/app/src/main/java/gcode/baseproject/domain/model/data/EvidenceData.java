package gcode.baseproject.domain.model.data;

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

public class EvidenceData {
    @SerializedName("cid")
    private  String Id;
    @SerializedName("ciddatospre")
    private  String FkQuestionData;
    @SerializedName("cimagen")
    private  String Image;
    @SerializedName("ccreacion")
    private DateTime Creation;

    public  void setId(String id){
        Id=id;
    }
    public String getId(){
        return Id;
    }
    public  void setFkQuestionData(String fkquestiondata){
        FkQuestionData=fkquestiondata;
    }
    public  String getFkQuestionData(){
        return FkQuestionData;
    }
    public void setImage(String image){
        Image=image;
    }
    public  String getImage(){
        return Image;
    }
    public  void setCreation(DateTime creation){
        Creation= creation;
    }
    public DateTime getCreation(){
        return Creation;
    }

}
