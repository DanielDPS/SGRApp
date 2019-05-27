package gcode.baseproject.view.viewmodel.dataFile;

import android.app.Application;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.repository.dataFile.FileDataRepository;
import gcode.baseproject.domain.repository.dataFile.IFileDataRepository;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FileDataViewModel extends BaseNetworkViewModel {

    private IFileDataRepository iFileDataRepository;
    public FileDataViewModel(@NonNull Application application) {
        super(application);
        iFileDataRepository = new FileDataRepository();
    }
    public  void AddFileData(FileDataEntity fileDataEntity){
        iFileDataRepository.AddFileData(fileDataEntity);
    }
    public Completable AddFile(FileDataEntity file){
        return iFileDataRepository.AddFile(file);
    }
    public List<FileDataEntity> getFilesByFkFormatData(String fkFormatData){return  iFileDataRepository.getFilesByFk(fkFormatData);}

    public  Integer getCountFilesByIDS(String idFile,String idFormatData){
        return  iFileDataRepository.getCountFilesByIds(idFile,idFormatData);
    }
    public  Completable UpdateFileData(FileDataEntity file){
       return iFileDataRepository.UpdateFileData(file);
    }

    private MutableLiveData<List<FileDataEntity >> getAllFilesByIdFormatData = new MutableLiveData<>();

    public  void LoadFilesById(String id ){
        Single<List<FileDataEntity>> allfiles = iFileDataRepository.getAllFilesById(id)
                .map(new Function<List<FileDataEntity>, List<FileDataEntity>>() {
                    @Override
                    public List<FileDataEntity> apply(List<FileDataEntity> fileDataEntities) throws Exception {
                        return fileDataEntities;
                    }
                });

        allfiles.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObservableAllFiles());
    }
    public  MutableLiveData<List<FileDataEntity>> mgetAllFiles(){
        return  getAllFilesByIdFormatData;
    }
    public DisposableSingleObserver<List<FileDataEntity>> getObservableAllFiles(){
        return  new DisposableSingleObserver<List<FileDataEntity>>() {
            @Override
            public void onSuccess(List<FileDataEntity> fileDataEntities) {
                getAllFilesByIdFormatData.postValue(fileDataEntities);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        };
    }


}
