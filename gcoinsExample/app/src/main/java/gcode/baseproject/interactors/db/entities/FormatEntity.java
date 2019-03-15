package gcode.baseproject.interactors.db.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import gcode.baseproject.domain.model.formatSection.FormatSection;

@Entity(tableName = "tblformat")
public class FormatEntity implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private  String cId;
    @ColumnInfo(name = "description")
    private  String cDescription;
    @ColumnInfo(name = "edited")
    private  String cEdited;
    @ColumnInfo(name = "active")
    private  String cActive;
    @Ignore
    private List<FormatSection> cSections ;

    public String getCId() {
        return cId ;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getCDescription() {
        return cDescription;
    }

    public void setCDescription(String cDescription) {
        this.cDescription = cDescription;
    }

    public String getCEdited() {
        return cEdited;
    }

    public void setCEdited(String cEdited) {
        this.cEdited = cEdited;
    }

    public String getCActive() {
        return cActive;
    }

    public void setCActive(String cActive) {
        this.cActive = cActive;
    }

    public List<FormatSection> getcSections() {
        return cSections;
    }

    public void setcSections(List<FormatSection> cSections) {
        this.cSections = cSections;
    }
}
