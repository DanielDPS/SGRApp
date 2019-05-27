package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;

public class GetAnswersByFkQuestionAsyncTask extends AsyncTask<Void,Void, List<QuestionDataEntity>> {

    private  List<QuestionDataEntity> listAnswers;
    private  String fk;
    private QuestionDataDao questionDataDao;
    public  GetAnswersByFkQuestionAsyncTask (QuestionDataDao dao, String fk){
        this.questionDataDao = dao;
        this.fk = fk;
    }
    public  List<QuestionDataEntity> getListAnswers(){
        return this.listAnswers;
    }
    @Override
    protected List<QuestionDataEntity> doInBackground(Void... voids) {
        return  this.listAnswers = this.questionDataDao.getAnswersByFkQuestion(this.fk);
    }
}
