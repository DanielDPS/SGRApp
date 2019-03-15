package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.SectionDataDao;

public class GetCountSectionAsyncTask extends AsyncTask<Void,Void,Boolean> {


    private SectionDataDao sectionDataDaoAT;
    private Integer count;
    private String idseciton ;
    private  Integer  folio;
    public GetCountSectionAsyncTask(SectionDataDao dao,String id,Integer folio){
        this.sectionDataDaoAT=dao;
        this.idseciton= id;
        this.folio= folio;
    }
    public  Integer getCount(){
        return this.count;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        count =this.sectionDataDaoAT.getCountSectionData(this.idseciton,this.folio);
        return count>0;
    }


}
