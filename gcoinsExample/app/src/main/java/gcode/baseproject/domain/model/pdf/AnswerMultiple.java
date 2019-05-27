package gcode.baseproject.domain.model.pdf;

public class AnswerMultiple {
    private  String idAnswerMultiple;
    private  String ObservationMultiple;
    private  String TextAnswer;

    public String getTextAnswer() {
        return TextAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        TextAnswer = textAnswer;
    }

    public String getIdAnswerMultiple() {
        return idAnswerMultiple;
    }

    public void setIdAnswerMultiple(String idAnswerMultiple) {
        this.idAnswerMultiple = idAnswerMultiple;
    }

    public String getObservationMultiple() {
        return ObservationMultiple.equals("")?"(sin observación)":ObservationMultiple;
    }

    public void setObservationMultiple(String observationMultiple) {
        ObservationMultiple = observationMultiple;
    }
}
