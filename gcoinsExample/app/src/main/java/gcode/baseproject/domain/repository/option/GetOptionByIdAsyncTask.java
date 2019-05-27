package gcode.baseproject.domain.repository.option;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.OptionDao;
import gcode.baseproject.interactors.db.entities.OptionEntity;

public class GetOptionByIdAsyncTask extends AsyncTask<Void,Void, OptionEntity> {

    private OptionDao optionDao;
    private  String id;
    private  OptionEntity option;
    public  GetOptionByIdAsyncTask(OptionDao dao ,String id){
        this.optionDao = dao;
        this.id= id;
    }
    public  OptionEntity getOption(){
        return this.option;
    }
    @Override
    protected OptionEntity doInBackground(Void... voids) {
        this.option = this.optionDao.getOptionById(this.id);
        return this.option;
    }
}
