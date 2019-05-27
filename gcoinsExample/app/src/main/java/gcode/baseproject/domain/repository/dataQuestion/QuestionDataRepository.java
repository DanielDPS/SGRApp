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
    public void AddQuestionData(final QuestionDataEntity questionDataEntity) {
            questionDataDao.AddQuestionData(questionDataEntity);
    }


    @Override
    public Single<List<QuestionDataEntity>> getQuestionData() {
        return questionDataDao.getQuestionsData();
    }

    @Override
    public Integer getCountAnswersById(String fkQuestion, String fkFormatData) {
        GetCountAnswersAsyncTask task = new GetCountAnswersAsyncTask(questionDataDao,fkQuestion,fkFormatData);

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
    public QuestionDataEntity getCountQuestionsDataByFkQuestion(String fk, String fkFormatData) {
        GetCountQuestionsByFkQuestionAsyncTask task = new GetCountQuestionsByFkQuestionAsyncTask(questionDataDao,fk,fkFormatData);
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
    public QuestionDataEntity getQuestionDataObejctByIdQuestion(String id, String fkFormatData,String fkSectionData) {
        GetQuestionDataObjectAyncTask task = new GetQuestionDataObjectAyncTask(questionDataDao,id,fkFormatData,fkSectionData);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getQuestionDataEntity();
    }


    @Override
    public Completable UpdateQuestionData(final QuestionDataEntity questionDataEntity) {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                questionDataDao.UpdateQuestionData(questionDataEntity);
            }
        });
    }

    @Override
    public Integer getCountQuestionsMultiple(String fkQ, String fkfD, String fkD) {
        GetCountQuestionMultilpeAsyncTask task = new GetCountQuestionMultilpeAsyncTask(questionDataDao,fkQ,fkfD,fkD);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getCountQuestion();
    }

    @Override
    public List<QuestionDataEntity> getAnswersByFkQuestion(String fkQuestion) {
        GetAnswersByFkQuestionAsyncTask task= new GetAnswersByFkQuestionAsyncTask(questionDataDao,fkQuestion);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getListAnswers();

    }

    @Override
    public Integer getCOUNTMultipleQuestions( String fkFormatData, String fkSectionData) {
        GetCountMultipleQuestionsAsyncTask task= new GetCountMultipleQuestionsAsyncTask(questionDataDao,fkFormatData,fkSectionData);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getCountMultile();
    }


    @Override
    public List<QuestionDataEntity> getListAnswersByFkFormatData(String fkFormatData) {
        GetListAnswersByFkFormatDataAyncTask task = new GetListAnswersByFkFormatDataAyncTask(questionDataDao,fkFormatData);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getListAnswers();
    }

    @Override
    public Completable DeleteAnswersListByFkFormatData(final List<QuestionDataEntity> AnswersList) {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                questionDataDao.DeleteQuestionDataList(AnswersList);
            }
        });
    }

    //question multiple click
    @Override
    public Single<QuestionDataEntity> getAnswersMultiple(String fkQ, String fkFD, String fkSD) {
        return questionDataDao.getAnswersMultiple(fkQ,fkFD,fkSD);

    }
    //question multiple swipe
    @Override
    public Single<QuestionDataEntity> getAnswersMultipelSwipe(String fkq, String fkfd, String fksd) {
        return questionDataDao.getQuestionMultipleSwipe(fkq,fkfd,fksd);
    }

    @Override
    public Integer getCountQuestionMultipleForSwipe(String fkQuestion, String fkSectionData) {
        GetCountForMultipleSwipeAsyncTask task = new GetCountForMultipleSwipeAsyncTask(questionDataDao,fkQuestion,fkSectionData);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getCountMultipleSwipe();
    }

    @Override
    public QuestionDataEntity getAnswerObjectMultiple(String idQuestion, String idFormatData, String idSectionData) {
        GetAnswerObjectMultiple task = new GetAnswerObjectMultiple(questionDataDao,idQuestion,idFormatData,idSectionData);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getAnswer();
    }

    @Override
    public void UpdateQuestion(QuestionDataEntity answer) {
        questionDataDao.UpdateQuestionData(answer);
    }

    @Override
    public Integer getCountExists(String id, String fkFormatData, String fkSectionData) {
        GetExistsCountAsyncTask  task = new GetExistsCountAsyncTask(questionDataDao,id,fkFormatData,fkSectionData);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getCount();
    }

    @Override
    public Integer getCountExistsNormales(String id, String fkFormatData) {
        GetExistsNormalesAsyncTask task = new GetExistsNormalesAsyncTask(questionDataDao,id,fkFormatData);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getCount();
    }

    @Override
    public Integer getTotalAnswers(String id, String idSection) {
        GetTotalAnswersAsyncTask task = new GetTotalAnswersAsyncTask(questionDataDao,id,idSection);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getCount();
    }


}
