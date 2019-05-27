package gcode.baseproject.interactors.db.dao.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;
import io.reactivex.Single;

@Dao
public interface SectionDataDao {
    @Insert
    void AddSectionData(SectionDataEntity sectionDataEntity);
    @Update
    void UpdateSectionData(SectionDataEntity sectionDataEntity);
    @Delete
    void DeleteSectionDataList(List<SectionDataEntity> list);

    @Query("SELECT * FROM tblSectionData WHERE fksection = :fk AND fkFormatData=:fkFormatData ORDER BY folio DESC")
    Single<List<SectionDataEntity>> getAll(String fk,String fkFormatData);

    @Query("SELECT * FROM tblSectionData WHERE fksection = :fkSection AND fkFormatData =:fkFormatd")
    List<SectionDataEntity> getSectionsListByFKSectionAndFkFD (String fkSection,String fkFormatd);

    @Query("SELECT * FROM tblSectionData WHERE fksection = :fkSection")
    List<SectionDataEntity> getListSectionsByIdSection (String fkSection);

    @Query("SELECT COUNT(*) FROM tblSectionData WHERE fksection=:idsection AND folio= :folio")
    Integer getCountSectionData(String idsection,int folio);



    @Query("SELECT COUNT(*) FROM tblSectionData WHERE fksection= :fk AND fkFormatData=:fkFormatD")
    Single<Integer> getCountSectionDataByFkSection(String fk,String fkFormatD);


    @Query("SELECT COUNT(*) FROM tblSectionData WHERE fksection= :fks AND id= :id")
    Integer getCountSD(String fks,String id);


    @Query("SELECT COUNT(*) FROM tblSectionData WHERE  fksection= :fks AND fkFormatData=:idS")
    Integer getCountSDForSection(String fks,String idS);
    @Query("SELECT COUNT(*) FROM tblSectionData WHERE id = :idSectionData")
    Integer getCountSectionDATAById(String idSectionData);

    //object

    @Query("SELECT * FROM tblSectionData WHERE id=:idSectionData")
    SectionDataEntity getObjectSectionById(String idSectionData);


    @Query("SELECT * FROM tblSectionData WHERE fksection = :fkSection")
    SectionDataEntity getObjectSectionDataByFkSection(String fkSection);

    @Query("SELECT * FROM tblsectiondata WHERE fksection=:idSectionData")
    List<SectionDataEntity> getListSectionDataById(String idSectionData);

    @Query("SELECT * FROM tblSectionData WHERE fkFormatData = :fkFormatData")
    List<SectionDataEntity> getSectionsDataByFkFormatData(String fkFormatData);


    //get last folio
    @Query("SELECT MAX(folio) FROM tblSectionData WHERE fksection =:fkSection AND fkFormatData =:fkFormatData LIMIT 1")
    Integer getLastFolio(String fkSection,String fkFormatData);

    @Query("SELECT id FROM tblSectionData WHERE folio = :foliop")
    String getIdSectionData (int foliop);




}
