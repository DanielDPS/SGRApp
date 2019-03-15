package gcode.baseproject.domain.repository.format;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.QuestionDao;

public class GetQuestionsByFkSectionAsyncTask extends AsyncTask<String,Void,Integer> {


    private QuestionDao questionDaoAT;
    private  Integer  total;
    public GetQuestionsByFkSectionAsyncTask(QuestionDao dao){
        this.questionDaoAT= dao;

    }
    public Integer getTotal(){
        return total;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        total = this.questionDaoAT.getQuestions(strings[0]);
        return total;
    }
}
