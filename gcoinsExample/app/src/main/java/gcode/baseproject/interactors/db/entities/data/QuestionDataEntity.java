package gcode.baseproject.interactors.db.entities.data;

 import androidx.annotation.IntDef;
 import androidx.annotation.NonNull;
 import androidx.annotation.Nullable;
 import androidx.databinding.ObservableField;
 import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
 import androidx.room.Ignore;
 import androidx.room.Index;
 import androidx.room.PrimaryKey;
 import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;

@Entity(tableName = "tblQuestionData",
         indices = {@Index(value ="fkformatData"),
         @Index(value = "fksectionData"),
                 @Index(value = "fkQuestion")
         }
         ,
         foreignKeys = {
         @ForeignKey(
                 entity = FormatDataEntity.class,
                 parentColumns = "fdid",
                 childColumns = "fkformatData",
                 onDelete = ForeignKey.CASCADE
         ),
                @ForeignKey(
                        entity =  SectionDataEntity.class,
                        parentColumns = "id",
                        childColumns = "fksectionData",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity =  QuestionEntity.class,
                        parentColumns = "id",
                        childColumns = "fkQuestion",
                        onDelete = ForeignKey.CASCADE
                )
        }

)
public class QuestionDataEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private  String Id;
    @ColumnInfo(name = "fkformatData")
    private String FkformatData;
    @ColumnInfo(name = "fksectionData")
    private String FksectionData;
    @ColumnInfo(name = "fkQuestion")
    private  String Fkquestion;
    @ColumnInfo(name = "fkOption")
    private String Fkoption;
    @Nullable
    @ColumnInfo(name = "textQuestion")
    private String TextQuestion;
    @Nullable
    @ColumnInfo(name = "observation")
    private String Observation;
    @ColumnInfo(name = "edited")
    private  String Edited;
    @Ignore
    private  boolean Checked;

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFkformatData() {
        return FkformatData;
    }

    public void setFkformatData(String fkformatData) {
        FkformatData = fkformatData;
    }

    public String getFksectionData() {
        return FksectionData;
    }

    public void setFksectionData(String fksectionData) {
        FksectionData = fksectionData;
    }

    public String getFkquestion() {
        return Fkquestion;
    }

    public void setFkquestion(String fkquestion) {
        Fkquestion = fkquestion;
    }

    public String getFkoption() {
        return Fkoption;
    }

    public void setFkoption(String fkoption) {
        Fkoption = fkoption;
    }

    @Nullable
    public String getTextQuestion() {
        return TextQuestion;
    }

    public void setTextQuestion(@Nullable String textQuestion) {
        TextQuestion = textQuestion;
    }

    @Nullable
    public String getObservation() {
        return Observation==null?"(sin observacion)": Observation;
    }

    public void setObservation(@Nullable String observation) {
        Observation = observation;
    }

    public String getEdited() {
        return Edited;
    }

    public void setEdited(String edited) {
        Edited = edited;
    }
}
