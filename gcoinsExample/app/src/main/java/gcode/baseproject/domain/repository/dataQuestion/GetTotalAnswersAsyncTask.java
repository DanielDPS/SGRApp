package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;

public class GetTotalAnswersAsyncTask extends AsyncTask<Void,Void,Integer> {

    private QuestionDataDao questionDataDao;
    private  String id;
    private  Integer count;
    private  String fkSection;
    public  GetTotalAnswersAsyncTask(QuestionDataDao dao, String id,String idSection){
        this.id=id;
        this.questionDataDao= dao;
        this.fkSection = idSection;
    }
    public  Integer getCount(){
        return  count;
    }
    @Override
    protected Integer doInBackground(Void... voids) {
        return count = questionDataDao.getTotalAnswers(id,fkSection);
    }
}
