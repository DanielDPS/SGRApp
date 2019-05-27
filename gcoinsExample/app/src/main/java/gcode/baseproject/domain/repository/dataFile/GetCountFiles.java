package gcode.baseproject.domain.repository.dataFile;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.FileDataDao;

public class GetCountFiles extends AsyncTask<Void,Void,Integer> {


    private FileDataDao fileDataDao;
    private  Integer countFiles;
    private String fkFormatData;
    private  String idFile;
    public GetCountFiles (FileDataDao dao, String idFile,String fkFormatData){
        this.fileDataDao = dao;
        this.idFile =idFile;
        this.fkFormatData = fkFormatData;
    }
    public  Integer getCountFiles(){
        return  this.countFiles;
    }
    @Override
    protected Integer doInBackground(Void... voids) {
        return countFiles = fileDataDao.getCountFilesByIds(idFile,fkFormatData);
    }
}
