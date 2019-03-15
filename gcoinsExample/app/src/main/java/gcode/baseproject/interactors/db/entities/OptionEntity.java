package gcode.baseproject.interactors.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tblOption",
    indices = @Index(value = {"fkquestion"}),
    foreignKeys = @ForeignKey(
            entity = QuestionEntity.class,
            parentColumns = "id",
            childColumns = "fkquestion"
    )
)
public class OptionEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private  String Id;
    @ColumnInfo(name = "fkquestion")
    private  String Fkquestion;
    @ColumnInfo(name = "description")
    private  String Description;

    @NonNull
    public String getId() {
        return Id;
    }

    public void setId(@NonNull String id) {
        Id = id;
    }

    public String getFkquestion() {
        return Fkquestion;
    }

    public void setFkquestion(String fkquestion) {
        Fkquestion = fkquestion;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
