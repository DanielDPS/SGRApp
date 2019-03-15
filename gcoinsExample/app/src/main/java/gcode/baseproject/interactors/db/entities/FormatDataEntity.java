package gcode.baseproject.interactors.db.entities;


import java.io.BufferedReader;
import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tblformatData",
         indices = {@Index(value = "fkformat"),@Index(value = "fkcustomer")},
         foreignKeys ={ @ForeignKey(
                entity = FormatEntity.class,
                parentColumns = "id",
                childColumns = "fkformat",
                onDelete =  ForeignKey.CASCADE

        ) ,@ForeignKey (
        entity = CustomerEntity.class,
        parentColumns = "id",
        childColumns = "fkcustomer",
        onDelete =  ForeignKey.CASCADE)}


)
public class FormatDataEntity implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "fdid")
    private  String Fdid;
    @ColumnInfo(name = "fkformat")
    private String Fkformat;
    @ColumnInfo(name = "fkcustomer")
    private  String FkCustomer;
    @ColumnInfo(name = "identifier")
    private  String Identifier;
    @ColumnInfo(name = "edited")
    private  String Edited;
    @ColumnInfo(name = "fkuser")
    private  String Fkuser;
    @ColumnInfo(name = "estado01")
    private  int Estado01;
    @ColumnInfo(name = "estado02")
    private  int Estado02;
    @Ignore
    private  String textState01;
    @Ignore
    private  String textState02;

    public String getTextState01() {
        switch (Estado01){
            case 0:
                textState01="PENDIENTE";
                break;
            case 1:
                textState01 ="FINALIZADO";
                break;
        }
        return textState01;
    }


    public String getTextState02() {
        switch (Estado02)
        {
            case 0:
                textState02 ="SIN SINCRONIZAR";
                break;
            case 1:
                textState02="SINCRONIZANDO";
                break;
            case 2 :
                textState02 = "SINCRONIZADO";
                break;
        }
        return textState02;
    }



    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    public String getFdid() {
        return Fdid;
    }

    public void setFdid(String fdid) {
        Fdid = fdid;
    }

    public String getFkformat() {
        return Fkformat;
    }

    public void setFkformat(String fkformat) {
        Fkformat = fkformat;
    }

    public String getFkCustomer() {
        return FkCustomer;
    }

    public void setFkCustomer(String fkCustomer) {
        FkCustomer = fkCustomer;
    }

    public String getEdited() {
        return Edited;
    }

    public void setEdited(String edited) {
        Edited = edited;
    }

    public String getFkuser() {
        return Fkuser;
    }

    public void setFkuser(String fkuser) {
        Fkuser = fkuser;
    }

    public int getEstado01() {

        return Estado01;
    }

    public void setEstado01(int estado01) {
        Estado01 = estado01;
    }

    public int getEstado02() {
        return Estado02;
    }

    public void setEstado02(int estado02) {
        Estado02 = estado02;
    }
}
