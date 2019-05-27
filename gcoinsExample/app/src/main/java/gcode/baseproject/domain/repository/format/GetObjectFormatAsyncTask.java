package gcode.baseproject.domain.repository.format;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.FormatDao;
import gcode.baseproject.interactors.db.entities.FormatEntity;

public class GetObjectFormatAsyncTask extends AsyncTask<Void,Void, FormatEntity> {

    private FormatDao formatDao;
    private  FormatEntity format;
    private  String id ;
    public  GetObjectFormatAsyncTask(FormatDao dao,String id){
        this.formatDao = dao;
        this.id= id;
    }
    public  FormatEntity getFormat(){
        return  this.format;
    }
    @Override
    protected FormatEntity doInBackground(Void... voids) {
        this.format = this.formatDao.getObjectFormatById(this.id);
        return  this.format;
    }
}
