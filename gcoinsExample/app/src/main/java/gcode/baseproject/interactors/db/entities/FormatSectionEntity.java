package gcode.baseproject.interactors.db.entities;


import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import gcode.baseproject.domain.model.question.Question;

@Entity(tableName = "tblFormatSection",
        indices = {@Index(value = {"fkformat"})},
        foreignKeys = @ForeignKey(entity = FormatEntity.class,
                parentColumns = "id",
                childColumns = "fkformat",
                onDelete = ForeignKey.CASCADE
        )

)public class FormatSectionEntity implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo (name = "id")
    private  String Id;
    @ColumnInfo(name = "fkformat")
    private  String Fkformat;
    @ColumnInfo(name = "description")
    private String Description;
    @ColumnInfo(name = "order")
    private  Integer Order;
    @ColumnInfo(name = "ismultiple")
    private  String Ismultiple;
    @ColumnInfo(name = "image")
    private String Image;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Nullable
    @ColumnInfo(name = "multipledescription")
    private  String Multipledescription;


    @Nullable
    @ColumnInfo(name =  "ismultipleref")
    private  String Ismultipleref;

    @ColumnInfo(name = "edited")
    private String Edited;

    public String getEdited() {
        return Edited;
    }

    public void setEdited(String edited) {
        Edited = edited;
    }

    @Ignore
    private List<Question> cQuestions;

    public  void setId(String Id){this.Id =Id;}
    public String getId(){return this.Id;}
    public  void setFkformat(String Fkformat){this.Fkformat=Fkformat;}
    public  String getFkformat(){return this.Fkformat;}
    public  void setDescription(String Description){this.Description=Description;}
    public String getDescription(){return this.Description;}
    public  void setOrder(Integer Order){this.Order=Order;}
    public  Integer getOrder(){return this.Order;}

    public  void setIsmultiple(@Nullable String Ismultiple){this.Ismultiple=Ismultiple;}
    @Nullable
    public  String getIsmultiple(){return this.Ismultiple;}

    public  void setMultipledescription(@Nullable String Multipledescription){this.Multipledescription=Multipledescription;}
    @Nullable
    public  String getMultipledescription(){return this.Multipledescription;}

    public  void setIsmultipleref(@Nullable String Ismultipleref){this.Ismultipleref =Ismultipleref;}
    @Nullable
    public  String getIsmultipleref(){return this.Ismultipleref;}

    public List<Question> getcQuestions() {
        return cQuestions;
    }

    public void setcQuestions(List<Question> cQuestions) {
        this.cQuestions = cQuestions;
    }
}
