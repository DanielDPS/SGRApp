package gcode.baseproject.interactors.db.entities.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;

@Entity(tableName = "tblSectionData",
        indices = {@Index(value = "fksection")},
        foreignKeys = {
         @ForeignKey(
                 entity = FormatSectionEntity.class,
                 parentColumns = "id",
                 childColumns = "fksection",
                 onDelete = ForeignKey.CASCADE
         )
        })
public class SectionDataEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private  long Id;
    @ColumnInfo(name = "fksection")
    private  String Fksection;
    @ColumnInfo(name = "reference")
    private  String Reference;
    @ColumnInfo(name = "folio")
    private int Folio;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getFksection() {
        return Fksection;
    }

    public void setFksection(String fksection) {
        Fksection = fksection;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public int getFolio() {
        return Folio;
    }

    public void setFolio(int folio) {
        Folio = folio;
    }
}
