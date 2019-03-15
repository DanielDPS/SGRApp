package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.SectionDataDao;

public class GetCountSectionDataByFkSectionAsyncTask extends AsyncTask<Void,Void,Integer> {

    private SectionDataDao sectionDataDao;
    private  Integer CountSectionData;
    private  String id;
    public  GetCountSectionDataByFkSectionAsyncTask(SectionDataDao dao,String fk){
        this.sectionDataDao = dao;
        this.id=fk;
    }
    public  Integer getCountSectionData(){
        return this.CountSectionData;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        this.CountSectionData = this.sectionDataDao.getCountSectionDataByFkSection(this.id);
        return this.CountSectionData;
    }
}
