package gcode.baseproject.domain.repository.dataQuestion;

import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.QuestionDao;
import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public class QuestionDataRepository  implements  IQuestionDataRepository{

    private QuestionDataDao questionDataDao ;
    public  QuestionDataRepository(){
        questionDataDao = ApplicationDatabase.getDatabase().getQuestionDataDao();
    }


    @Override
    public Completable AddQuestionData(final QuestionDataEntity questionDataEntity) {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                try{

                          questionDataDao.AddQuestionData(questionDataEntity);



                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("TAG20","ERRORRRRRR: "+e.getLocalizedMessage());
                }
            }
        });
    }


    @Override
    public Single<List<QuestionDataEntity>> getQuestionData() {
        return questionDataDao.getQuestionsData();
    }

    @Override
    public Integer getCountAnswersById(String fkQuestion) {
        GetCountAnswersAsyncTask task = new GetCountAnswersAsyncTask(questionDataDao,fkQuestion);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getAnswersCount();
    }

    @Override
    public Integer getCountQuestionsDataByFkQuestion(String fk) {
        GetCountQuestionsByFkQuestionAsyncTask task = new GetCountQuestionsByFkQuestionAsyncTask(questionDataDao,fk);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getCountQuestions();
    }

    @Override
    public QuestionDataEntity getQuestionDataObejctByIdQuestion(String id) {
        GetQuestionDataObjectAyncTask task = new GetQuestionDataObjectAyncTask(questionDataDao,id);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getQuestionDataEntity();
    }


}
