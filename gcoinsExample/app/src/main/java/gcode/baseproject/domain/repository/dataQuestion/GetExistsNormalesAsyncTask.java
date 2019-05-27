package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;

public class GetExistsNormalesAsyncTask extends AsyncTask<Void,Void,Integer> {


    private  Integer count;
    private  String id;
    private  String fkFormat;
    private QuestionDataDao questionDataDao;
    public  GetExistsNormalesAsyncTask(QuestionDataDao dao,String id,String fkFormat){
        this.questionDataDao= dao;
        this.id=id;
        this.fkFormat=fkFormat;
    }
    public  Integer getCount(){
        return  count;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return count = questionDataDao.ExistsAnswerNormales(id,fkFormat);
    }
}
