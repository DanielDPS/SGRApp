package gcode.baseproject.domain.model.question;

import android.widget.EditText;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import gcode.baseproject.domain.model.option.Option;

public class Question {
    @SerializedName("cid")
    private  String Id;
    @SerializedName("cdescripcion")
    private String Description;
    @SerializedName("challazgo")
    private  String Hallazgo;
    @SerializedName("cfundamentojur")
    private String Fundament;
    @SerializedName("ccritico")
    private  String Critical;
    @SerializedName("ctipocam")
    private String FieldType;
    @SerializedName("cimagen")
    private  String Image;
    @SerializedName("cmodificacion")
    private String Edited;
    @SerializedName("opciones")
    private List<Option> options;

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

    public String getHallazgo() {
        return Hallazgo;
    }

    public void setHallazgo(String hallazgo) {
        Hallazgo = hallazgo;
    }

    public String getFundament() {
        return Fundament;
    }

    public void setFundament(String fundament) {
        Fundament = fundament;
    }

    public String isCritical() {
        return Critical;
    }

    public void setCritical(String critical) {
        Critical = critical;
    }

    public String getFieldType() {
        return FieldType;
    }

    public void setFieldType(String fieldType) {
        FieldType = fieldType;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getEdited() {
        return Edited;
    }

    public void setEdited(String edited) {
        Edited = edited;
    }

    public List<gcode.baseproject.domain.model.option.Option> getOptions() {
        return options;
    }

    public void setOptions(List<gcode.baseproject.domain.model.option.Option> options) {
        this.options = options;
    }
}
