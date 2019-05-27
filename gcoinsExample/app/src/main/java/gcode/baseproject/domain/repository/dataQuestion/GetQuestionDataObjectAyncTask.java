package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;

public class GetQuestionDataObjectAyncTask extends AsyncTask<Void,Void, QuestionDataEntity> {
    private QuestionDataDao questionDataDao;
    private  QuestionDataEntity questionDataEntity;
    private  String fkQuestion;
    private  String fkd;
    private  String fks;
    public GetQuestionDataObjectAyncTask(QuestionDataDao dao , String fk,String fkd,String fks){
        this.questionDataDao= dao;
        this.fkQuestion= fk;
        this.fkd= fkd;
        this.fks= fks;

    }
    public  QuestionDataEntity getQuestionDataEntity(){
        return this.questionDataEntity;
    }
    @Override
    protected QuestionDataEntity doInBackground(Void... voids) {
        this.questionDataEntity = this.questionDataDao.getQuestionDataObejct(this.fkQuestion,this.fkd,this.fks);
        return this.questionDataEntity;
    }
}
