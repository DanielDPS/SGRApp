package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;

public class GetExistsCountAsyncTask extends AsyncTask<Void,Void,Integer> {

    private  Integer count;
    private  String id;
    private  String fkFormat;
    private  String fkSectionData;
    private QuestionDataDao questionDataDao;
    public  GetExistsCountAsyncTask(QuestionDataDao dao,String id,String fkFormat,String fkSectionData){
        this.questionDataDao= dao;
        this.id=id;
        this.fkFormat=fkFormat;
        this.fkSectionData = fkSectionData;
    }
    public  Integer getCount(){
        return  count;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return count = questionDataDao.ExistsAnswer(id,fkFormat,fkSectionData);
    }
}
