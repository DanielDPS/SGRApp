package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;

public class GetCountQuestionsByFkQuestionAsyncTask extends AsyncTask<Void,Void,QuestionDataEntity> {

    private QuestionDataDao questionDataDao;
    private QuestionDataEntity countQuestions;
    private  String fk;
    private String fkFormat;
     public  GetCountQuestionsByFkQuestionAsyncTask(QuestionDataDao dao,String fk ,String fkFormat ){
        this.questionDataDao = dao;
        this.fk=fk;
        this.fkFormat= fkFormat;
      }
    public  QuestionDataEntity getCountQuestions(){
        return this.countQuestions;
    }
    @Override
    protected QuestionDataEntity doInBackground(Void... voids) {
        this.countQuestions = this.questionDataDao.getCountQuestionData(this.fk,this.fkFormat);
        return this.countQuestions;

    }
}
