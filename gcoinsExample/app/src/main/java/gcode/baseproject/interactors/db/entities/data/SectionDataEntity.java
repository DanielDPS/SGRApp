package gcode.baseproject.interactors.db.entities.data;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;

@Entity(tableName = "tblSectionData",
        indices = {@Index(value = "fksection"),@Index(value = "fkFormatData")},
        foreignKeys = {
         @ForeignKey(
                 entity = FormatSectionEntity.class,
                 parentColumns = "id",
                 childColumns = "fksection",
                 onDelete = ForeignKey.CASCADE
         ),
                @ForeignKey(
                        entity = FormatDataEntity.class,
                        parentColumns = "fdid",
                        childColumns = "fkFormatData",
                        onDelete = ForeignKey.CASCADE
                )
        })
public class SectionDataEntity implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private  String Id;
    @ColumnInfo(name = "fksection")
    private  String Fksection;
    @ColumnInfo(name = "fkFormatData")
    private  String FkFormatData;
    @Nullable
    @ColumnInfo(name = "reference")
    private  String Reference;
    @ColumnInfo(name = "folio")
    private int Folio;

    public String getFkFormatData() {
        return FkFormatData;
    }

    public void setFkFormatData(String fkFormatData) {
        FkFormatData = fkFormatData;
    }

    public String getId() {
        return Id.equals(null) ? null : Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFksection() {
        return Fksection;
    }

    public void setFksection(String fksection) {
        Fksection = fksection;
    }

    @Nullable
    public String getReference() {
        return Reference;
    }

    public void setReference(@Nullable String reference) {
        Reference = reference;
    }

    public int getFolio() {
        return Folio;
    }

    public void setFolio(int folio) {
        Folio = folio;
    }
}
