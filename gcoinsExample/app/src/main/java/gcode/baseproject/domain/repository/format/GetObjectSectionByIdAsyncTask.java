package gcode.baseproject.domain.repository.format;

import android.os.AsyncTask;

import gcode.baseproject.domain.model.formatSection.FormatSection;
import gcode.baseproject.interactors.db.dao.FormatDao;
import gcode.baseproject.interactors.db.dao.FormatSectionDao;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;

public class GetObjectSectionByIdAsyncTask extends AsyncTask<Void,Void, FormatSectionEntity> {

    private FormatSectionDao formatSectionDao;
    private FormatSectionEntity formatSectionEntity;
    private  String id;
    public  GetObjectSectionByIdAsyncTask(FormatSectionDao dao,String id ){
        this.formatSectionDao = dao;
        this.id=id;
    }
    public  FormatSectionEntity getFormatSectionEntity(){
        return  this.formatSectionEntity;
    }
    @Override
    protected FormatSectionEntity doInBackground(Void... voids) {
        this.formatSectionEntity = this.formatSectionDao.getObejctSectionById(this.id);
        return  this.formatSectionEntity;
    }
}
