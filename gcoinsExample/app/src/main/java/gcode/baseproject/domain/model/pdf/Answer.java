package gcode.baseproject.domain.model.pdf;

import java.util.List;

import androidx.annotation.Nullable;

public class Answer {


    // Answer
    @Nullable
    private String text;


    // Desacription in case of answer no
    @Nullable
    private String observation;


    @Nullable
    private List<String> imagesUrls;


    public void setText(@Nullable String text) {
        this.text = text;
    }


    @Nullable
    public String getText() {
        return text;
    }


    public void setObservation(@Nullable String observation) {
        this.observation = observation;
    }

    @Nullable
    public String getObservation() {
        return observation.equals("") ?"(sin observación)":observation;
    }


    public void setImagesUrls(@Nullable List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    @Nullable
    public List<String> getImagesUrls() {
        return imagesUrls;
    }
}
