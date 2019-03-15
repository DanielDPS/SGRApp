package gcode.baseproject.domain.repository.dataQuestion;

import java.util.List;
import java.util.concurrent.Callable;

import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface IQuestionDataRepository {
    Completable AddQuestionData(QuestionDataEntity questionDataEntity);
    Single<List<QuestionDataEntity>> getQuestionData();
    Integer getCountAnswersById(String fkQuestion);
    Integer getCountQuestionsDataByFkQuestion(String fk);
    QuestionDataEntity getQuestionDataObejctByIdQuestion (String id);

}
