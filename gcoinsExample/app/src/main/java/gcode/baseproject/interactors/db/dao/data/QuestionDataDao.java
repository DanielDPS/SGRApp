package gcode.baseproject.interactors.db.dao.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
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

    @Query("SELECT * FROM tblQuestionData WHERE fkformatData= :fkFormatData")
    List<QuestionDataEntity> getListAnswersByFkFormatData(String fkFormatData);

    @Delete
    void DeleteQuestionDataList(List<QuestionDataEntity> listQuestionData);

    //preguntas normales
    @Query("SELECT * FROM tblQuestionData   WHERE fkQuestion= :fkQuestion AND fkformatData=:idFormat ")
    QuestionDataEntity getCountQuestionData(String fkQuestion,String idFormat);

    //preguntas multiples
    @Query("SELECT COUNT(*) FROM tblQuestionData WHERE fkQuestion= :fkQuestion   AND fksectionData =:idSectionData AND fkformatData =:fkFormat")
    Integer getCountQuestionDataMultiple(String fkQuestion,String fkFormat,String idSectionData);




    //multiples swipe
    @Query("SELECT * FROM tblQuestionData WHERE fkQuestion= :fkQuestion AND fkformatData =:fkFormatData  AND fksectionData =:idSectionData ")
    Single<QuestionDataEntity> getAnswersMultiple(String fkQuestion,String fkFormatData, String idSectionData);



    //esgfgfgfdhdf
    @Query("SELECT COUNT(*) FROM tblQuestionData LEFT JOIN tblQuestion ON tblQuestionData.fkQuestion=tblQuestion.id WHERE  tblQuestion.fksection=:fkQuestion AND fkformatData=:fkFormatData ")
    Integer getQuestionsCountByFkSecionData(String fkQuestion,String fkFormatData);




    //COUNT ANSWERS BUTTON CREATE MUTLIPLE
    @Query("SELECT COUNT(*) FROM tblQuestionData  WHERE  fkformatData=:fkFormatData AND fksectionData=:fkSectionD")
    Integer getCountMultipleQuestions(String fkFormatData,String fkSectionD);





    @Query("SELECT * FROM tblQuestionData WHERE fkQuestion=:fkQuestion  AND fkformatData =:fkFormatD OR fksectionData = :fkSection ")
    QuestionDataEntity getQuestionDataObejct(String fkQuestion,String fkFormatD,String fkSection);













    @Query("SELECT * FROM tblQuestionData WHERE fkQuestion = :fk")
    List<QuestionDataEntity> getAnswersByFkQuestion(String fk);



    @Query("SELECT * FROM tblQuestionData WHERE fkQuestion=:fkQuestion  AND fkformatData =:fkFormatD ")
    QuestionDataEntity getSectionsByFKS(String fkQuestion,String fkFormatD);




    @Query("SELECT * FROM tblQuestionData WHERE fkQuestion = :questionId AND fkformatData = :fkFormatData AND fksectionData= :fkSectionData")
    QuestionDataEntity getAnswerObjectMultiple(String questionId,String fkFormatData,String fkSectionData);






    @Query("SELECT * FROM tblQuestionData WHERE fkQuestion=:fkQ AND fkformatData = :fkFD AND fksectionData=:fkSD")
    Single<QuestionDataEntity > getQuestionMultipleSwipe(String fkQ,String fkFD,String fkSD);




    @Query("SELECT COUNT(*) FROM tblQuestionData WHERE fkQuestion= :fkQ AND fksectionData = :fkSD")
    Integer getCountForMultipleSwipe(String fkQ,String fkSD);















    //VERIFICAR SI EXISTEN DATOS
    @Query("SELECT COUNT(*) FROM tblquestiondata WHERE fkQuestion=:idAnswer AND fkformatData=:fkFormat AND fksectionData=:fkSectionData")
    Integer ExistsAnswer(String idAnswer,String fkFormat,String fkSectionData);


    @Query("SELECT COUNT(*) FROM tblquestiondata WHERE fkQuestion=:idAnswer AND fkformatData=:fkFormat")
    Integer ExistsAnswerNormales(String idAnswer,String fkFormat);


    @Query("SELECT COUNT(*) FROM tblQuestionData WHERE fkformatData = :fkFormat AND fksectionData=:fkSectionData")
    Integer getTotalAnswers(String fkFormat,String fkSectionData);





}
