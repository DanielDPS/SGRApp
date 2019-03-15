package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;

public class GetCountQuestionsByFkQuestionAsyncTask extends AsyncTask<Void,Void,Integer> {

    private QuestionDataDao questionDataDao;
    private  Integer countQuestions;
    private  String fk;
    public  GetCountQuestionsByFkQuestionAsyncTask(QuestionDataDao dao,String fk){
        this.questionDataDao = dao;
        this.fk=fk;
    }
    public  Integer getCountQuestions(){
        return this.countQuestions;
    }
    @Override
    protected Integer doInBackground(Void... voids) {
        this.countQuestions = this.questionDataDao.getCountQuestionData(this.fk);
        return this.countQuestions;

    }
}
