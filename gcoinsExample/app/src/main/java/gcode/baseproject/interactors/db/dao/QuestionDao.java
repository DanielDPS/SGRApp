package gcode.baseproject.interactors.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import io.reactivex.Single;

@Dao
public interface QuestionDao {

    @Insert
    void AddQuestion(QuestionEntity questionEntity);
    @Update
    void UpdateQuestion(QuestionEntity questionEntity);
    @Query("SELECT COUNT(*) FROM tblQuestion WHERE id =:id")
    Integer getCountQuestions(String id);
    @Query("SELECT * FROM tblQuestion")
    Single<List<QuestionEntity>> getAllQuestions();
    @Query("SELECT * FROM tblQuestion WHERE fksection = :idsection")
    Single<List<QuestionEntity>> getquestionsByFkSection(String idsection);
    @Query("SELECT COUNT(*) FROM tblQuestion WHERE fksection = :id")
    Integer getQuestions(String id);
    @Query("SELECT COUNT(*) FROM tblQuestion WHERE fksection =:fkSection")
    Integer getCountByFkSection(String fkSection);
    @Query("SELECT id FROM tblQuestion WHERE fksection= :fk")
    String getIdQuestionByFkSection(String fk);

}
