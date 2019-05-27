package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.SectionDataDao;

public class GetCountSectionDataByFkSectionAsyncTask extends AsyncTask<Void,Void,Integer> {

    private SectionDataDao sectionDataDao;
    private  Integer CountSectionData;
    private  String id;
    private  String idS;
    public  GetCountSectionDataByFkSectionAsyncTask(SectionDataDao dao,String fk,String ids){
        this.sectionDataDao = dao;
        this.id=fk;
        this.idS = ids;
    }
    public  Integer getCountSectionData(){
        return this.CountSectionData;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        this.CountSectionData = this.sectionDataDao.getCountSD(this.id,this.idS);
        return this.CountSectionData;
    }
}
