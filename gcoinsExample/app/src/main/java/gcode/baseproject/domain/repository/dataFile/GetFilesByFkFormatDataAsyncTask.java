package gcode.baseproject.domain.repository.dataFile;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.interactors.db.dao.data.FileDataDao;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;

public class GetFilesByFkFormatDataAsyncTask extends AsyncTask<Void,Void, List<FileDataEntity>> {

    private  List<FileDataEntity > files;
    private  String fk;
    private FileDataDao fileDataDao;
    public  GetFilesByFkFormatDataAsyncTask (FileDataDao dao,String fk){
        this.fileDataDao= dao;
        this.fk =fk;
    }
    public  List<FileDataEntity> getFiles(){
        return  this.files;
    }
    @Override
    protected List<FileDataEntity> doInBackground(Void... voids) {
        return this.files = this.fileDataDao.getFilesByFkFormatData(this.fk);
    }
}
