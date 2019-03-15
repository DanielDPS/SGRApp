package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;

public class AddQuestionDataAsyncTask extends AsyncTask<Void,Void,Void> {

    private QuestionDataDao questionDaoAT;
    private    QuestionDataEntity questionDataEntity;
    public AddQuestionDataAsyncTask(QuestionDataDao dao, QuestionDataEntity questionDataEntity){
        this.questionDaoAT =dao;
        this.questionDataEntity = questionDataEntity;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        this.questionDaoAT.AddQuestionData(questionDataEntity);
        return null;
    }


}
