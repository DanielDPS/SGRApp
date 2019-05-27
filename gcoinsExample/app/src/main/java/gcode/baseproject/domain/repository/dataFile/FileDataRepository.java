package gcode.baseproject.domain.repository.dataFile;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.data.FileDataDao;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public class FileDataRepository  implements   IFileDataRepository{


    private FileDataDao fileDataDao;
    public FileDataRepository (){
        fileDataDao = ApplicationDatabase.getDatabase().getFileDataDao();
    }
    @Override
    public Completable AddFile(final FileDataEntity file) {
        return  Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                fileDataDao.AddFileData(file);

            }
        });
    }
    @Override
    public List<FileDataEntity> getFilesByFk(String fkFormatData) {
        GetFilesByFkFormatDataAsyncTask task= new GetFilesByFkFormatDataAsyncTask(fileDataDao,fkFormatData);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getFiles();
    }

    @Override
    public void AddFileData(FileDataEntity file) {
        //fileDataDao.AddFileData(file);
    }

    @Override
    public Integer getCountFilesByIds(String idFile, String idFormatData) {
        GetCountFiles task = new GetCountFiles(fileDataDao,idFile,idFormatData);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getCountFiles();
    }

    @Override
    public Completable UpdateFileData(final FileDataEntity file) {

        return  Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                fileDataDao.UpdateFileData(file);
            }
        });
    }

    @Override
    public Single<List<FileDataEntity>> getAllFilesById(String id) {
        return  fileDataDao.getAllFiles(id);
    }

}
