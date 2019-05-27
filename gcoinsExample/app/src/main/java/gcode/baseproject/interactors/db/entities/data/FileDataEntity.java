package gcode.baseproject.interactors.db.entities.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;

@Entity(tableName = "tblFileData",
        indices = {@Index(value = "fkFormatData")},
        foreignKeys = {
        @ForeignKey(
                entity = FormatDataEntity.class,
                parentColumns = "fdid",
                childColumns = "fkFormatData"
        )
        }
)
public class FileDataEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private  String Id;

    @ColumnInfo(name = "fkFormatData")
    private  String FkFormatData;

    @ColumnInfo(name = "type")
    private String Type;

    @ColumnInfo(name = "file")
    private String File;

    @NonNull
    public String getId() {
        return Id;
    }

    public void setId(@NonNull String id) {
        Id = id;
    }

    public String getFkFormatData() {
        return FkFormatData;
    }

    public void setFkFormatData(String fkFormatData) {
        FkFormatData = fkFormatData;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getFile() {
        return File;
    }

    public void setFile(String file) {
        File = file;
    }
}
