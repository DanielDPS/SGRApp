package gcode.baseproject.domain.repository.format;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.interactors.db.dao.FormatSectionDao;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;

public class GetListSectionByFkFormatAsyncTask extends AsyncTask<Void,Void, List<FormatSectionEntity>> {
    private FormatSectionDao sectionDao;
    private  List<FormatSectionEntity> sectionEntityList;
    private  String fkFormat ;
    public  GetListSectionByFkFormatAsyncTask(FormatSectionDao dao,String fk){
        this.sectionDao = dao;
        this.fkFormat = fk;
    }
    public  List<FormatSectionEntity> getSectionEntityList(){
        return  this.sectionEntityList;
    }
    @Override
    protected List<FormatSectionEntity> doInBackground(Void... voids) {
        this.sectionEntityList = this.sectionDao.getListSectionsByFkFormat(this.fkFormat);
        return  this.sectionEntityList;
    }
}
