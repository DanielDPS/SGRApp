package gcode.baseproject.domain.repository.dataFormat;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.FormatDataDao;

public class GetCountFormatDataByIdFormatAndCustomerAsyncTask extends AsyncTask<Void,Void,Integer> {

    private FormatDataDao formatDataDao;
    private  String fkf;
    private  String fkc;
    private  Integer count;
    private  String identifier;
    public  GetCountFormatDataByIdFormatAndCustomerAsyncTask(FormatDataDao dao,String fkformat,String fkcustomer,String identificator){
        this.formatDataDao= dao;
        this.fkf = fkformat;
        this.fkc = fkcustomer;
        this.identifier = identificator;
    }
    public  Integer getCount(){
        return this.count;
    }
    @Override
    protected Integer doInBackground(Void... voids) {
        count = this.formatDataDao.checkIfExists(this.fkf,this.fkc,this.identifier);
        return count;
    }
}
