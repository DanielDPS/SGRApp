package gcode.baseproject.interactors.db.dao.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;
import io.reactivex.Single;

@Dao
public interface EvidenceDataDao {
    @Insert
    void AddEvidence(EvidenceDataEntity evidenceDataEntity);
    @Query("SELECT COUNT(*) FROM tblEvidenceData WHERE fkquestionData=:fk ")
    Integer getCountEvidence(String fk);
    @Query("SELECT * FROM tblEvidenceData WHERE fkquestionData =:fkdata")
    Single<List<EvidenceDataEntity>> getEvidence(String fkdata);

}
