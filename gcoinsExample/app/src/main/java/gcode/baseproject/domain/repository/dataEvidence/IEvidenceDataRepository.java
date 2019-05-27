package gcode.baseproject.domain.repository.dataEvidence;

import java.util.List;

import gcode.baseproject.domain.model.data.EvidenceData;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface IEvidenceDataRepository {

    Completable addEvidenceData(EvidenceDataEntity evidenceDataEntity);
    Integer getCountEvidenceData(String fkquestionData);
    Single<List<EvidenceDataEntity>> getEvidenceList(String fk);
    Completable DeleteEvidence(EvidenceDataEntity evidenceDataEntity);
    Completable UpdateEvidence(EvidenceDataEntity evidenceDataEntity);
    List<EvidenceDataEntity> getEvidenceListByFkQuestionData(String fk);
}
