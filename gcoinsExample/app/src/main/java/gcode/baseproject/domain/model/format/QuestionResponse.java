package gcode.baseproject.domain.model.format;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import gcode.baseproject.domain.model.question.Question;

public class QuestionResponse {
    @SerializedName("status")
    private  boolean Status;
    @SerializedName("message")
    private  String Message;
    @SerializedName("result")
    private List<Question> questions;

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
