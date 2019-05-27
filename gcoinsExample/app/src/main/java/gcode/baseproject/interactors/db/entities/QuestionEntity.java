package gcode.baseproject.interactors.db.entities;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import gcode.baseproject.domain.model.formatSection.FormatSection;
import gcode.baseproject.domain.model.option.Option;

@Entity(tableName = "tblQuestion"
,
        indices = {@Index(value = {"fksection"})},
        foreignKeys = @ForeignKey(
      entity = FormatSectionEntity.class,
      parentColumns = "id",
        childColumns = "fksection",
        onDelete = ForeignKey.CASCADE
)
)
public class QuestionEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private  String Id;
    @ColumnInfo(name =  "fksection")
    private  String Fksection;
    @ColumnInfo(name = "description")
    private  String Description;
    @Nullable
    @ColumnInfo(name = "hallazgo")
    private  String Hallazgo;
    @ColumnInfo(name = "fundament")
    private  String Fundament;
    @ColumnInfo(name = "critical")
    private  String Critical;
    @ColumnInfo(name = "fieldtype")
    private  String Fieldtype;
    @ColumnInfo(name = "porder")
    private  int Order;
    @ColumnInfo(name ="image")
    private String Image;
    @ColumnInfo(name = "edited")
    private  String Edited;
    @Ignore
    private List<Option> optionsList;

    public List<Option> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<Option> optionsList) {
        this.optionsList = optionsList;
    }

    @NonNull
    public String getId() {
        return Id==null ? null : Id;
    }

    public void setId(@NonNull String id) {
        Id = id;
    }

    public String getFksection() {
        return Fksection;
    }

    public void setFksection(String fksection) {
        Fksection = fksection;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Nullable
    public String getHallazgo() {
        return Hallazgo;
    }

    public void setHallazgo(@Nullable String hallazgo) {
        Hallazgo = hallazgo;
    }

    public String getFundament() {
        return Fundament;
    }

    public void setFundament(String fundament) {
        Fundament = fundament;
    }

    public String getCritical() {
        return Critical;
    }

    public void setCritical(String critical) {
        Critical = critical;
    }

    public String getFieldtype() {
        return Fieldtype;
    }

    public void setFieldtype(String fieldtype) {
        Fieldtype = fieldtype;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int pOrder) {
        Order = pOrder;
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
}
