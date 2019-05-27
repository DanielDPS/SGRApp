package gcode.baseproject.domain.repository.dataSection;


import java.util.List;

import gcode.baseproject.interactors.db.dao.data.SectionDataDao;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface ISectionDataRepository {
    Completable AddSectionDataDB(SectionDataEntity sectionDataEntity);
    Single<List<SectionDataEntity>> getAllById(String id,String fk);
    Boolean CheckIfExistsData(String id,int folio);
    Thread UpdateSectionDataDB(SectionDataEntity sectionDataEntity);
    Single<Integer> getCountSectionData(String fk,String fkFormatData);
    List<SectionDataEntity> getListSectionsById(String fk);
    Integer getCountSDById (String fk,String id);
    SectionDataEntity getObjectSectionById(String fk);
    Integer getCOUNTForSection(String fk,String idSectionData);
    Integer getCountSectionDataById(String id );
    List<SectionDataEntity> getListSectionDataById(String id);
    SectionDataEntity getObjectSectionDataByID(String id);
    List<SectionDataEntity > getListSectionsByFkFormatData(String fkFormatData);
    Completable DeleteAllSecionList(List<SectionDataEntity> listSectionData);
    //GET LAST FOLIO
    Integer getLastFolio(String fkSection,String fkFormatData);
    String getId(int folio);
}
