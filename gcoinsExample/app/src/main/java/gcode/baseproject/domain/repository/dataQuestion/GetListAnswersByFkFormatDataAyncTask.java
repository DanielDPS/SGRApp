package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;

public class GetListAnswersByFkFormatDataAyncTask extends AsyncTask<Void,Void, List<QuestionDataEntity>> {

    private  List<QuestionDataEntity >listAnswers;
    private  String fkFormatData;
    private QuestionDataDao questionDataDao;
    public  GetListAnswersByFkFormatDataAyncTask (QuestionDataDao dao,String fkFormatData){
        this.questionDataDao = dao;
        this.fkFormatData = fkFormatData;
    }
    public  List<QuestionDataEntity> getListAnswers(){
        return  this.listAnswers;
    }
    @Override
    protected List<QuestionDataEntity> doInBackground(Void... voids) {
        return this.listAnswers = questionDataDao.getListAnswersByFkFormatData(this.fkFormatData);
    }
}
