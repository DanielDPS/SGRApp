package gcode.baseproject.domain.repository.dataQuestion;

import java.util.List;
import java.util.concurrent.Callable;

import androidx.room.Query;
import gcode.baseproject.domain.model.data.QuestionData;
import gcode.baseproject.interactors.db.ApplicationDatabase_Impl;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface IQuestionDataRepository {
    void AddQuestionData(QuestionDataEntity questionDataEntity);
    Single<List<QuestionDataEntity>> getQuestionData();
    Integer getCountAnswersById(String fkQuestion,String fkFormatData);

    //preguntas normales
    QuestionDataEntity getCountQuestionsDataByFkQuestion(String fk,String fkFormatData);

    QuestionDataEntity getQuestionDataObejctByIdQuestion (String id,String fkFormatData,String fkSectionData);
    Completable UpdateQuestionData(QuestionDataEntity questionDataEntity);
    //preguntas multiples
    Integer getCountQuestionsMultiple(String fkQ,String fkfD,String fkD);
    //object

    //Answers List AsyncTask
    List<QuestionDataEntity> getAnswersByFkQuestion (String fkQuestion);
    //COUNT MULTIPLE
    Integer getCOUNTMultipleQuestions(String fkFormatData,String fkSectionData);

    List<QuestionDataEntity > getListAnswersByFkFormatData(String fkFormatData);
    Completable DeleteAnswersListByFkFormatData(List<QuestionDataEntity> AnswersList);

    //multiple click
    Single<QuestionDataEntity> getAnswersMultiple (String fkQ,String fkFD,String fkSD);
    //multiple swipe
    Single<QuestionDataEntity> getAnswersMultipelSwipe (String fkq,String fkfd,String fksd);


    Integer getCountQuestionMultipleForSwipe(String fkQuestion,String fkSectionData);

    QuestionDataEntity getAnswerObjectMultiple (String idQuestion,String idFormatData,String idSectionData);

    void UpdateQuestion(QuestionDataEntity answer);

    Integer getCountExists(String id,String fkFormatData,String fkSectionData);
    Integer getCountExistsNormales(String id,String fkFormatData);

    Integer getTotalAnswers(String id,String idSection);


}
