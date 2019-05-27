package gcode.baseproject.domain.repository.dataFile;

import java.util.List;

import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface IFileDataRepository {

    Completable AddFile(FileDataEntity file);
    List<FileDataEntity> getFilesByFk(String fkFormatData);
    void AddFileData(FileDataEntity file);
    Integer getCountFilesByIds(String idFile,String idFormatData);
    Completable UpdateFileData(FileDataEntity file );
    Single<List<FileDataEntity>>getAllFilesById(String id);
 }
