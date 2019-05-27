package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.SectionDataDao;

public class GetIdSectionAsyncTask extends AsyncTask<Void,Void,String > {

    private  int folio ;
    private  String id;
    private SectionDataDao sectionDataDao;
    public  GetIdSectionAsyncTask (SectionDataDao dao,int folio)
    {
        this.sectionDataDao= dao;
        this.folio = folio;
    }
    public  String getId(){
        return  id;
    }
    @Override
    protected String doInBackground(Void... voids) {
        return id = sectionDataDao.getIdSectionData(folio);
    }
}
