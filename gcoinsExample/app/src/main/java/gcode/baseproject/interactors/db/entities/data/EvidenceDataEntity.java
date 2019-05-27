package gcode.baseproject.interactors.db.entities.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tblEvidenceData",
    indices = {@Index(value = "fkquestionData")},
    foreignKeys = {
        @ForeignKey(
                entity = QuestionDataEntity.class,
                parentColumns = "id",
                childColumns = "fkquestionData",
                onDelete = ForeignKey.CASCADE
         )
    }
)
public class EvidenceDataEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private  long Id;
    @ColumnInfo(name = "fkquestionData")
    private String FkquestionData;
    @ColumnInfo(name = "imageUrl")
    private  String ImageUrl;
    @ColumnInfo(name = "creation")
    private  String Creation;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getFkquestionData() {
        return FkquestionData;
    }

    public void setFkquestionData(String fkquestionData) {
        FkquestionData = fkquestionData;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getCreation() {
        return Creation;
    }

    public void setCreation(String creation) {
        Creation = creation;
    }
}
