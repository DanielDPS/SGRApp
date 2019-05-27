package gcode.baseproject.domain.repository.dataAPI.addFormatData;

import android.os.AsyncTask;

import gcode.baseproject.domain.model.pdf.Format;
import gcode.baseproject.interactors.db.dao.data.FormatDataDao;

public class UpdateFormatDataAsyncTask extends AsyncTask<Void,Void,Void> {


    private FormatDataDao formatDataDao;
    private int state01;
    private  int state02;
    private  String endDate;
    private  String idFormatData;
    public  UpdateFormatDataAsyncTask(FormatDataDao dao,int s1,int s2,String endDate,String id){
        this.formatDataDao= dao;
        this.state01=s1;
        this.state02 =s2;
        this.endDate= endDate;
        this.idFormatData=id;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        formatDataDao.UpdateFormat(state01,state02,endDate,idFormatData);
        return null;
    }
}
