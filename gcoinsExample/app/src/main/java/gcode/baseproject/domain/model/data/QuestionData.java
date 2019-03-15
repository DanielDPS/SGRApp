package gcode.baseproject.domain.model.data;

import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.Observer;

public class QuestionData {

    @SerializedName("cid")
    private String Id;
    @SerializedName("ciddatosfor")
    private  String FkData;
    @SerializedName("ciddatgruppre")
    private  String FkQuestionGroupData;
    @SerializedName("cidpregunta")
    private String FkQuestion;
    @SerializedName("cidopcion")
    private String FkOption;
    @SerializedName("ctexto")
    private String Text;
    @SerializedName("cobservacion")
    private String Observation;
    @SerializedName("ccreacion")
    private DateTime Creation;
    @SerializedName("cmodificacion")
    private  DateTime Upgrade;

    public  void setId(String id){
        Id=id;
    }
    public String getId(){
        return Id;
    }
    public void setFkData(String fkdata){
        FkData=fkdata;
    }
    public  String getFkData(){
        return FkData;
    }
    public void setFkQuestionGroupData(String fkquestiongroupdata){
        FkQuestionGroupData=fkquestiongroupdata;
    }
    public  String getFkQuestionGroupData(){
        return FkQuestionGroupData;
    }
    public  void setFkQuestion(String fkquestion){
        FkQuestion= fkquestion;
    }
    public String getFkQuestion(){
        return FkQuestion;
    }
    public  void setFkOption(String fkoption){
        FkOption=fkoption;
    }
    public  String getFkOption(){
        return FkOption;
    }
    public  void setText(String text){
        Text= text;
    }
    public  String getText(){
        return Text;
    }
    public  void setObservation(String observation){
        Observation=observation;
    }
    public  String getObservation(){
        return Observation;
    }
    public  void setCreation(DateTime creation){
        Creation=  creation;
    }
    public  DateTime getCreation(){
        return Creation;
    }
    public  void setUpgrade(DateTime upgrade){
        Upgrade =upgrade;
    }
    public   DateTime getUpgrade(){
        return Upgrade;
    }

}
