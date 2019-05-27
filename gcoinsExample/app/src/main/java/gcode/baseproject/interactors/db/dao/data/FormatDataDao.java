package gcode.baseproject.interactors.db.dao.data;

import java.text.Normalizer;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
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
    @Delete
    void DeleteFormatData(FormatDataEntity formatDataEntity);
    @Query("SELECT *  FROM tblformatData")
    Single<List<FormatDataEntity>> getFormatsData();
    @Query("SELECT COUNT(*) FROM tblformatData")
    Integer getCountFormatsData();
    @Query("SELECT fdid FROM tblformatData")
    String getIdFormatData();
    @Query("SELECT COUNT(*) FROM tblformatData WHERE  fkformat=:fkf AND fkcustomer=:fkc AND identifier=:identificator")
    Integer checkIfExists(String fkf,String fkc,String identificator);
    @Query("SELECT * FROM tblformatData WHERE fdid = :fid")
    FormatDataEntity getObjectFormatDataById(String fid);
    @Query("UPDATE tblformatData SET estado01 =:state01 ,estado02 =:state02,endDate=:enddate WHERE fdid=:id")
    void UpdateFormat(int state01,int state02,String enddate,String id);

}
