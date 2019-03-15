package gcode.baseproject.interactors.db.entities;


import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
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
    @ColumnInfo(name = "multipledescription")
    private  String Multipledescription;
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
    public  void setIsmultiple(String Ismultiple){this.Ismultiple=Ismultiple;}
    public  String getIsmultiple(){return this.Ismultiple;}
    public  void setMultipledescription(String Multipledescription){this.Multipledescription=Multipledescription;}
    public  String getMultipledescription(){return this.Multipledescription;}
    public  void setIsmultipleref(String Ismultipleref){this.Ismultipleref =Ismultipleref;}
    public  String getIsmultipleref(){return this.Ismultipleref;}

    public List<Question> getcQuestions() {
        return cQuestions;
    }

    public void setcQuestions(List<Question> cQuestions) {
        this.cQuestions = cQuestions;
    }
}
