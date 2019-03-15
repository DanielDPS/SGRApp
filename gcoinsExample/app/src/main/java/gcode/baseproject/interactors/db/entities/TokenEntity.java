package gcode.baseproject.interactors.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Token")

public class TokenEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name =  "data")
    private String mToken;


    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getToken() {
        return mToken;
    }
}
