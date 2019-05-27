package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;

public class GetCountMultipleQuestionsAsyncTask extends AsyncTask<Void,Void,Integer> {


    private QuestionDataDao questionDataDao;
    private  String fkFormatData;
    private  String fkSectioData;
    private  Integer countMultile;
    private  String fkQuestion ;
    public  GetCountMultipleQuestionsAsyncTask (QuestionDataDao dao ,String fkFormatData,String fkSectioData){
        this.questionDataDao = dao;
         this.fkFormatData= fkFormatData;
        this.fkSectioData = fkSectioData;
    }
    public  Integer getCountMultile(){
        return  this.countMultile;
    }
    @Override
    protected Integer doInBackground(Void... voids) {
        return this.countMultile = this.questionDataDao.getCountMultipleQuestions(this.fkFormatData,this.fkSectioData);
    }
}
