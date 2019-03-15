package gcode.baseproject.interactors.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import gcode.baseproject.interactors.db.entities.FormatEntity;
@Dao
public interface FormatDao {

    @Insert
    void AddFormat(FormatEntity formatEntity);

    @Update
    void UpdateFormat(FormatEntity formatEntity);

    @Delete
    void RemoveFormat(FormatEntity formatEntity);

    @Query("SELECT * FROM tblformat")
    List<FormatEntity> getFormats();

    @Query("SELECT COUNT(*) FROM tblformat WHERE  id= :id")
    Integer getCountFormats(String id);

    @Query("SELECT edited FROM tblformat WHERE edited = (SELECT MAX(edited ) FROM tblformat) LIMIT 1")
    String getMaxLastUpdate();

    @Query("SELECT COUNT(*) FROM tblformat WHERE id = :id AND edited < :lastUpdate")
    Integer checkIfFormatsShouldUpdate(String id,String lastUpdate);
}
