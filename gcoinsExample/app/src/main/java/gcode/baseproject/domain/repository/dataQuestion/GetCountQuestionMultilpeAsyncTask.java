package gcode.baseproject.domain.repository.dataQuestion;

import android.os.AsyncTask;

import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;

public class GetCountQuestionMultilpeAsyncTask extends AsyncTask<Void,Void,Integer> {

    private QuestionDataDao questionDataDao;
    private Integer countQuestion ;
    private  String fkquestion;
    private  String fkSectionD;
    private  String fkFormatData;
    public  GetCountQuestionMultilpeAsyncTask(QuestionDataDao dao,String fkquestion,String fkFormatData,String fkSectionD){
        this.questionDataDao = dao;
        this.fkquestion =fkquestion;
        this.fkFormatData= fkFormatData;
        this.fkSectionD =fkSectionD;

    }

    public Integer getCountQuestion(){
        return  this.countQuestion;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        this.countQuestion= this.questionDataDao.getCountQuestionDataMultiple(this.fkquestion,this.fkFormatData,this.fkSectionD);
        return  this.countQuestion;
    }
}
