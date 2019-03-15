package gcode.baseproject.domain.model.format;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import gcode.baseproject.domain.model.formatSection.FormatSection;

public class Format  implements Serializable {

    @SerializedName("cid")
    private  String Id;
    @SerializedName("cdescripcion")
    private  String Description;
    @SerializedName("cmodificacion")
    private  String Upgrade;
    @SerializedName("cactivo")
    private  String Activo;
    @SerializedName("secciones")
    private List<FormatSection> sections;

    public void setId(String id){
        Id=id;
    }
    public String getId(){
        return Id;
    }
    public void setDescription(String description){
        Description = description;
    }
    public String getDescription(){
        return Description;
    }
    public void setUpgrade(String upgrade){
        Upgrade= upgrade;
    }
    public String getUpgrade(){
        return Upgrade;
    }
    public void setActivo(String activo){
        Activo= activo;
    }
    public String getActivo(){
        return Activo;
    }


    public List<FormatSection> getSections() {
        return sections;
    }

    public void setSections(List<FormatSection> sections) {
        this.sections = sections;
    }
}
