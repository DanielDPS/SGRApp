package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;

public class GetQuestionDataObjectAyncTask extends AsyncTask<Void,Void, QuestionDataEntity> {
    private QuestionDataDao questionDataDao;
    private  QuestionDataEntity questionDataEntity;
    private  String fkQuestion;
    public GetQuestionDataObjectAyncTask(QuestionDataDao dao , String fk){
        this.questionDataDao= dao;
        this.fkQuestion= fk;
    }
    public  QuestionDataEntity getQuestionDataEntity(){
        return this.questionDataEntity;
    }
    @Override
    protected QuestionDataEntity doInBackground(Void... voids) {
        this.questionDataEntity = this.questionDataDao.getQuestionDataObejct(this.fkQuestion);
        return this.questionDataEntity;
    }
}
