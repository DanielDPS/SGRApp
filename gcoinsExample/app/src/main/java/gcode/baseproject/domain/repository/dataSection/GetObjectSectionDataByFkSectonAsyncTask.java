package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

import gcode.baseproject.domain.repository.dataQuestion.GetQuestionDataObjectAyncTask;
import gcode.baseproject.interactors.db.dao.data.SectionDataDao;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;

public class GetObjectSectionDataByFkSectonAsyncTask extends AsyncTask<Void,Void, SectionDataEntity> {

    private SectionDataEntity sectionDataEntity;
    private SectionDataDao sectionDataDao;
    private  String fkSection;

    public GetObjectSectionDataByFkSectonAsyncTask (SectionDataDao dao,String fk){
        this.sectionDataDao = dao;
        this.fkSection = fk;
    }
    public  SectionDataEntity getSectionDataEntity(){
        return  this.sectionDataEntity;
    }

    @Override
    protected SectionDataEntity doInBackground(Void... voids) {
        this.sectionDataEntity = this.sectionDataDao.getObjectSectionDataByFkSection(this.fkSection);
        return  this.sectionDataEntity;
    }
}
