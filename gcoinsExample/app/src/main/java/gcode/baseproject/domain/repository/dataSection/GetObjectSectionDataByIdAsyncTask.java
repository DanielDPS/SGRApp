package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.SectionDataDao;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;

public class GetObjectSectionDataByIdAsyncTask extends AsyncTask<Void,Void, SectionDataEntity> {


    private  SectionDataEntity section ;
    private  String id;
    private SectionDataDao sectionDataDao;

    public  GetObjectSectionDataByIdAsyncTask (SectionDataDao dao,String id){
        this.sectionDataDao = dao;
        this.id= id;

    }
    public  SectionDataEntity getSection(){
        return this.section;
    }
    @Override
    protected SectionDataEntity doInBackground(Void... voids) {
        return this.section = this.sectionDataDao.getObjectSectionById(this.id);
    }
}
