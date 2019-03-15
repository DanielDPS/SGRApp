package gcode.baseproject.interactors.db.dao.data;

import java.util.List;

import androidx.room.Dao;
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

    @Query("SELECT * FROM tblSectionData WHERE fksection = :fk")
    Single<List<SectionDataEntity>> getAll(String fk);

    @Query("SELECT COUNT(*) FROM tblSectionData WHERE fksection=:idsection AND folio= :folio")
    Integer getCountSectionData(String idsection,int folio);
    @Query("SELECT COUNT(*) FROM tblSectionData WHERE fksection= :fk")
    Integer getCountSectionDataByFkSection(String fk);
}
