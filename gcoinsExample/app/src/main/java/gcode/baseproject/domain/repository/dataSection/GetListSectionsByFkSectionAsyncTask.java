package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.interactors.db.dao.data.SectionDataDao;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;

public class GetListSectionsByFkSectionAsyncTask extends AsyncTask<Void,Void, List<SectionDataEntity>> {

    private SectionDataDao dataDao ;
    private  List<SectionDataEntity> list;
    private  String fk;
    public  GetListSectionsByFkSectionAsyncTask(SectionDataDao dao,String fk){
        this.dataDao = dao;
        this.fk = fk;
    }
    public  List<SectionDataEntity >getList(){
        return this.list;
    }
    @Override
    protected List<SectionDataEntity> doInBackground(Void... voids) {
        this.list = this.dataDao.getListSectionsByIdSection(this.fk);
        return  this.list;
    }
}
