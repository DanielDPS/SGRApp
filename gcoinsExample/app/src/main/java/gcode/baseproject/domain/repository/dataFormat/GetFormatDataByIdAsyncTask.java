package gcode.baseproject.domain.repository.dataFormat;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.FormatDataDao;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;

public class GetFormatDataByIdAsyncTask extends AsyncTask<Void,Void, FormatDataEntity> {

    private FormatDataDao formatDataDao;
    private FormatDataEntity formatDataEntity;
    private  String id;
    public GetFormatDataByIdAsyncTask(FormatDataDao dao,String id){
        this.formatDataDao= dao;
        this.id =id;
    }
    public  FormatDataEntity getFormatDataEntity(){
        return this.formatDataEntity;
    }
    @Override
    protected FormatDataEntity doInBackground(Void... voids) {
        this.formatDataEntity = this.formatDataDao.getObjectFormatDataById(this.id);
        return  this.formatDataEntity;
    }
}
