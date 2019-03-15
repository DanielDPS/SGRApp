package gcode.baseproject.interactors.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import io.reactivex.Single;

@Dao
public interface FormatSectionDao {

    @Insert
    void AddFormatSection(FormatSectionEntity formatSectionEntity);
    @Update
    void UpdateFormatSection (FormatSectionEntity formatSectionEntity);
    @Delete
    void RemoveFormatSection (FormatSectionEntity formatSectionEntity);
    @Query("SELECT * FROM tblFormatSection WHERE fkformat = :idformat")
    Single<List<FormatSectionEntity >> getFormatSections(String idformat);
    @Query("SELECT COUNT(*)  FROM tblFormatSection WHERE id = :id")
    Integer getCountFormatSections(String id);
}
