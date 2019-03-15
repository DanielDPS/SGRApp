package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;

public class GetCountAnswersAsyncTask extends AsyncTask<Void,Void,Integer> {


    private QuestionDataDao questionDataDao;
    private  Integer answersCount;
     private  String fkSection;

    public  GetCountAnswersAsyncTask(QuestionDataDao dao,String fkSection){
        this.questionDataDao = dao;
         this.fkSection = fkSection;
     }
    public Integer getAnswersCount(){
        return this.answersCount;
    }
    @Override
    protected Integer doInBackground(Void... voids) {
        this.answersCount = this.questionDataDao.getQuestionsCountByFkSecionData(this.fkSection);
        return this.answersCount;
    }
}
