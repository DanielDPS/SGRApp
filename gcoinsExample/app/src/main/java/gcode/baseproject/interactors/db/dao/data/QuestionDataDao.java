package gcode.baseproject.interactors.db.dao.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import io.reactivex.Single;

@Dao
public interface QuestionDataDao {

    @Insert
    void AddQuestionData(QuestionDataEntity questionDataEntity);
    @Update
    void UpdateQuestionData(QuestionDataEntity questionDataEntity);
    @Query("SELECT * FROM tblQuestionData")
    Single<List<QuestionDataEntity>> getQuestionsData();
    @Query("SELECT COUNT(*) FROM tblQuestionData WHERE fkQuestion= :fk")
    Integer getCountQuestionData(String fk);
    @Query("SELECT COUNT(*) FROM tblQuestionData LEFT JOIN tblQuestion ON tblQuestionData.fkQuestion=tblQuestion.id WHERE  tblQuestion.fksection=:fkQuestion")
    Integer getQuestionsCountByFkSecionData(String fkQuestion);
    @Query("SELECT * FROM tblQuestionData WHERE fkQuestion=:fkQuestion")
    QuestionDataEntity getQuestionDataObejct(String fkQuestion);



}
