package gcode.baseproject.domain.repository.format;

import android.os.AsyncTask;
import android.print.PrinterId;

import java.util.List;

import gcode.baseproject.interactors.db.dao.FormatSectionDao;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;

public class GetFormatSectionsAsyncTask extends AsyncTask<Void,Void, List<FormatSectionEntity>> {
    private FormatSectionDao formatSectionDaoAT;
    private  List<FormatSectionEntity>  list;

    public GetFormatSectionsAsyncTask(FormatSectionDao dao){
        this.formatSectionDaoAT = dao;

    }
    public List<FormatSectionEntity >getList(){
        return this.list;
    }

    @Override
    protected List<FormatSectionEntity> doInBackground(Void... voids) {
        return null;
    }
}
