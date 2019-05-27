package gcode.baseproject.domain.model.pdf;

import java.util.List;

import androidx.annotation.Nullable;

public class Question {

    private String id;
    private String title;
    private int order;
    @Nullable
    private String found;
    @Nullable String Hallazgo;

    @Nullable
    public String getHallazgo() {
        return Hallazgo.equals("") ? "(no aplica)":Hallazgo;
    }

    public void setHallazgo(@Nullable String hallazgo) {
        Hallazgo = hallazgo;
    }

    private boolean isCritical;
    private String type;
    private List<Option> options;
    private Answer answer;
    private  Answer answerMultiple ;

    public Answer getAnswerMultiple() {
        return answerMultiple;
    }

    public void setAnswerMultiple(Answer answerMultiple) {
        this.answerMultiple = answerMultiple;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setFound(@Nullable String found) {
        this.found = found;
    }

    @Nullable
    public String getFound() {
        return found;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public void setOptions(List<Option> options) {
        this.options = options;
    }


    public List<Option> getOptions() {
        return options;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }


    public Answer getAnswer() {
        return answer;
    }
}
