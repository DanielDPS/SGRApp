package gcode.baseproject.domain.repository.format;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.interactors.db.dao.QuestionDao;
import gcode.baseproject.interactors.db.entities.QuestionEntity;

public class GetListQuestionsByFkSectionAyncTask extends AsyncTask<Void,Void, List<QuestionEntity>> {

    private  List<QuestionEntity> list;
    private  String fk;
    private QuestionDao questionDao;
    public  GetListQuestionsByFkSectionAyncTask(QuestionDao dao,String fk){
        this.questionDao= dao;
        this.fk=fk;
    }
    public  List<QuestionEntity> getList(){
        return this.list;
    }
    @Override
    protected List<QuestionEntity> doInBackground(Void... voids) {
        this.list = this.questionDao.getQuestionsByIdSection(this.fk);
        return this.list;
    }
}
