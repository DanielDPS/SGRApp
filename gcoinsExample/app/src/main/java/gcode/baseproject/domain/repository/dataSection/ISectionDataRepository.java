package gcode.baseproject.domain.repository.dataSection;


import java.util.List;

import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface ISectionDataRepository {
    Completable AddSectionDataDB(SectionDataEntity sectionDataEntity);
    Single<List<SectionDataEntity>> getAllById(String id);
    Boolean CheckIfExistsData(String id,int folio);
    Thread UpdateSectionDataDB(SectionDataEntity sectionDataEntity);
    Integer getCountSectionData(String fk);
}
