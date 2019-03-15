package gcode.baseproject.domain.model.formatSection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import gcode.baseproject.domain.model.question.Question;

public class FormatSection {

    @SerializedName("cid")
    private  String Id;
    @SerializedName("cdescripcion")
    private  String Description;
    @SerializedName("corden")
    private  int Order;
    @SerializedName("cdescripcionmul")
    private  String MultipleDescription;
    @SerializedName("ctienerefmul")
    private  String IsMultipleReference;
    @SerializedName("cmodificacion")
    private String cEdited ;

    public String getcEdited() {
        return cEdited;
    }

    public void setcEdited(String cEdited) {
        this.cEdited = cEdited;
    }

    @SerializedName("preguntas")
    private List<Question> questions;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }


    public String getMultipleDescription() {
        return MultipleDescription;
    }

    public String getIsMultipleReference() {
        return IsMultipleReference;
    }

    public void setIsMultipleReference(String isMultipleReference) {
        IsMultipleReference = isMultipleReference;
    }

    public void setMultipleDescription(String multipleDescription) {
        MultipleDescription = multipleDescription;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
