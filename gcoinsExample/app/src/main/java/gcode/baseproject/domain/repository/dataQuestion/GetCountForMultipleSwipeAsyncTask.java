package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;

public class GetCountForMultipleSwipeAsyncTask extends AsyncTask<Void,Void,Integer> {


    private QuestionDataDao questionDataDao;
    private  Integer countMultipleSwipe;
    private  String fkQuestion;
    private  String fkFormatData;
    private  String fkSectionData;
    public  GetCountForMultipleSwipeAsyncTask (QuestionDataDao dao,String fkQuestion,String fkSectionData){
        this.questionDataDao= dao;
        this.fkQuestion = fkQuestion;
        this.fkSectionData = fkSectionData;

    }
    public  Integer getCountMultipleSwipe(){
        return  this.countMultipleSwipe;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return countMultipleSwipe = questionDataDao.getCountForMultipleSwipe(fkQuestion,fkSectionData);
    }
}
