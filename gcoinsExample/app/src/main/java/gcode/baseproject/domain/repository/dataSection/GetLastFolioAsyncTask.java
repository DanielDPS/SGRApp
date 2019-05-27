package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.SectionDataDao;

public class GetLastFolioAsyncTask extends AsyncTask<Void,Void,Integer> {


    private SectionDataDao sectionDataDao;
    private  Integer folio ;
    private  String fkSection;
    private  String fkFormatData;
    public  GetLastFolioAsyncTask(SectionDataDao dao,String fkSection,String fkFormatData){
        this.sectionDataDao = dao;
        this.fkSection = fkSection;
        this.fkFormatData = fkFormatData;
    }
    public  Integer getLastFolio(){
        return  folio;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return folio = sectionDataDao.getLastFolio(fkSection,fkFormatData);
    }
}
