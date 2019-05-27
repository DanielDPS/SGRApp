package gcode.baseproject.domain.repository.format;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.interactors.db.dao.OptionDao;
import gcode.baseproject.interactors.db.entities.OptionEntity;

public class GetOptionsListByFkQuesitonAsyncTask extends AsyncTask<Void,Void, List<OptionEntity>> {


    private List<OptionEntity> options;
    private  String fk;
    private OptionDao optionDao;
    public GetOptionsListByFkQuesitonAsyncTask(OptionDao dao,String fk){
        this.optionDao =dao;
        this.fk=fk;
    }
    public  List<OptionEntity> getOptions(){
        return  this.options;
    }



    @Override
    protected List<OptionEntity> doInBackground(Void... voids) {
        this.options= this.optionDao.getListOptionsByFkQuestion(this.fk);
        return  this.options;
    }
}
