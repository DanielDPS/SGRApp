package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.SectionDataDao;

public class GetCountForSectionAsyncTask extends AsyncTask<Void,Void,Integer> {

    private SectionDataDao sectionDataDao;
    private  Integer count;
    private  String fkSection;
    private  String idSectionData;
    public  GetCountForSectionAsyncTask(SectionDataDao dao,String fk,String idSectionData){
        this.sectionDataDao =dao;
        this.fkSection= fk;
        this.idSectionData = idSectionData;

    }
    public  Integer getCount(){
        return  this.count;
    }
    @Override
    protected Integer doInBackground(Void... voids) {
        this.count = this.sectionDataDao.getCountSDForSection(this.fkSection,this.idSectionData);
        return  this.count;
    }
}
