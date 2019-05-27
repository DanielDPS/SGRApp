package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;

public class GetAnswerObjectMultiple extends AsyncTask<Void,Void, QuestionDataEntity> {

    private QuestionDataDao questionDataDao;
    private  String idQuestion;
    private  String idFormatData;
    private  String idSectionData;
    private  QuestionDataEntity answer;

    public  GetAnswerObjectMultiple (QuestionDataDao dao,String idQuestion,String idFormatData,String idSectionData){
        this.questionDataDao = dao;
        this.idQuestion = idQuestion;
        this.idFormatData = idFormatData;
        this.idSectionData = idSectionData;
    }

    public  QuestionDataEntity getAnswer(){
        return  answer;
    }

    @Override
    protected QuestionDataEntity doInBackground(Void... voids) {
        return answer = questionDataDao.getAnswerObjectMultiple(idQuestion,idFormatData,idSectionData);

    }
}
