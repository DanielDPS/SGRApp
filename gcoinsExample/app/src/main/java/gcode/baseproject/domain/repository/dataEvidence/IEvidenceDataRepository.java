package gcode.baseproject.domain.repository.dataEvidence;

import java.util.List;

import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface IEvidenceDataRepository {

    Completable addEvidenceData(EvidenceDataEntity evidenceDataEntity);
    Integer getCountEvidenceData(String fkquestionData);
    Single<List<EvidenceDataEntity>> getEvidenceList(String fk);
}
