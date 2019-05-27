package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.interactors.db.dao.data.SectionDataDao;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;

public class GetListSectionByFkFormatDataAsyncTask extends AsyncTask<Void,Void, List<SectionDataEntity>> {

    private  List<SectionDataEntity> list;
    private  String FkFormatData;
    private SectionDataDao sectionDataDao;
    public  GetListSectionByFkFormatDataAsyncTask(SectionDataDao dao,String fk){
        this.sectionDataDao = dao;
        this.FkFormatData = fk;
    }
    public  List<SectionDataEntity> getList(){
        return  this.list;
    }
    @Override
    protected List<SectionDataEntity> doInBackground(Void... voids) {
        return  this.list = sectionDataDao.getSectionsDataByFkFormatData(this.FkFormatData);
    }
}
