package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.SectionDataDao;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;

public class GetCountSectionDataByIdAyncTask extends AsyncTask<Void,Void, Integer> {

    private SectionDataDao dataDao;
    private  Integer countSection;
    private  String id ;
    public  GetCountSectionDataByIdAyncTask(SectionDataDao dao,String id){
        this.dataDao = dao;
        this.id=id;
    }
    public  Integer getCountSection(){
        return  this.countSection;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        this.countSection = this.dataDao.getCountSectionDATAById(this.id);
        return  this.countSection;
    }
}
