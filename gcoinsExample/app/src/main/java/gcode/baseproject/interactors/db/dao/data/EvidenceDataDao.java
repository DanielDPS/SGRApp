package gcode.baseproject.interactors.db.dao.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import gcode.baseproject.domain.model.data.EvidenceData;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface EvidenceDataDao {
    @Insert
    void AddEvidence(EvidenceDataEntity evidenceDataEntity);
    @Update
    void UpdateEvidence(EvidenceDataEntity evidenceDataEntity);
    @Delete
    void DeleteEvidence(EvidenceDataEntity evidenceDataEntity);
    @Query("SELECT COUNT(*) FROM tblEvidenceData WHERE fkquestionData=:fk ")
    Integer getCountEvidence(String fk);
    @Query("SELECT * FROM tblEvidenceData WHERE fkquestionData =:fkdata")
    Single<List<EvidenceDataEntity>> getEvidence(String fkdata);
    @Query("SELECT * FROM tblEvidenceData WHERE fkquestionData=:fk")
    List<EvidenceDataEntity> getEvidenceListByFkQuestionData(String fk);
    @Query("SELECT imageUrl FROM tblEvidenceData WHERE fkquestionData = :fkQuestionData")
    List<String> getImagesByFkAnswer(String fkQuestionData);

    @Query("SELECT imageUrl FROM tblEvidenceData WHERE fkquestionData = :fkQuestionData")
    List<String> getImagesNormales(String fkQuestionData);

    @Query("SELECT COUNT(*) FROM tblEvidenceData WHERE id=:idEvidence")
    Integer checkIfExistsEvidence(long idEvidence);


}
