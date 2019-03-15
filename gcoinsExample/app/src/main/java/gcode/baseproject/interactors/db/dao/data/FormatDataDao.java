package gcode.baseproject.interactors.db.dao.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import gcode.baseproject.domain.model.format.FormatData;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import io.reactivex.Single;

@Dao
public interface FormatDataDao {

    @Insert
    void AddFormatData(FormatDataEntity formatDataEntity);
    @Update
    void UpdateFormatDat(FormatDataEntity formatDataEntity);
    @Query("SELECT *  FROM tblformatData")
    Single<List<FormatDataEntity>> getFormatsData();
    @Query("SELECT COUNT(*) FROM tblformatData")
    Integer getCountFormatsData();
    @Query("SELECT fdid FROM tblformatData")
    String getIdFormatData();
}
