package gcode.baseproject.interactors.db.dao.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;
import io.reactivex.Single;

@Dao
public interface FileDataDao {
    @Insert
    void AddFileData(FileDataEntity fileDataEntity);
    @Update void UpdateFileData(FileDataEntity fileDataEntity);
    @Query("SELECT * FROM tblFileData WHERE fkFormatData=:idF")
    List<FileDataEntity> getFilesByFkFormatData(String idF);
    @Query("SELECT COUNT(*) FROM tblFileData WHERE id =:idFile AND fkFormatData = :fkFormatData")
    Integer getCountFilesByIds(String idFile,String fkFormatData);
    @Query("SELECT * FROM tblfiledata WHERE fkFormatData = :fkFormatData")
    Single<List<FileDataEntity>> getAllFiles(String fkFormatData);

}
