package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.interactors.db.dao.data.SectionDataDao;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;

public class GetListSectionDataByIdAsyncTask extends AsyncTask<Void,Void, List<SectionDataEntity>> {

    private  List<SectionDataEntity> sectionList;
    private  String id;
    private SectionDataDao sectionDataDao;
    public  GetListSectionDataByIdAsyncTask (SectionDataDao dao,String id){
        this.sectionDataDao = dao;
        this.id=id;
    }

    public  List<SectionDataEntity> getSection(){
        return this.sectionList;
    }

    @Override
    protected List<SectionDataEntity> doInBackground(Void... voids) {
        return this.sectionList = this.sectionDataDao.getListSectionDataById(this.id);
    }
}
