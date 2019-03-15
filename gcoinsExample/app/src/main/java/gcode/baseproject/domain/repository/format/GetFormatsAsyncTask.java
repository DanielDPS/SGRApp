package gcode.baseproject.domain.repository.format;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.interactors.db.dao.FormatDao;
import gcode.baseproject.interactors.db.entities.FormatEntity;

public class GetFormatsAsyncTask extends AsyncTask<Void,Void, List<FormatEntity>> {

    private FormatDao formatDaoAT;
    private  List<FormatEntity >formatEntities;
    public GetFormatsAsyncTask(FormatDao dao){
        this.formatDaoAT = dao;
    }
    public List<FormatEntity> getFormatEntities(){
        return this.formatEntities;
    }

    @Override
    protected List<FormatEntity> doInBackground(Void... voids) {
        formatEntities = formatDaoAT.getFormats();
        return formatEntities;
    }
}
