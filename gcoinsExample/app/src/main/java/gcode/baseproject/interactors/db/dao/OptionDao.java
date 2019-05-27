package gcode.baseproject.interactors.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import io.reactivex.Single;

@Dao
public interface OptionDao {

    @Insert
    void AddOption(OptionEntity optionEntity);
    @Update
    void UpdateOption(OptionEntity optionEntity);
    @Query("SELECT * FROM tblOption WHERE fkquestion = :id")
    Single<List<OptionEntity>>getOptionsByQuestion(String id);
    @Query("SELECT * FROM tblOption WHERE fkquestion = :id")
    OptionEntity getOptionById(String id);
    @Query("SELECT * FROM tblOption WHERE fkquestion =:fk")
    List<OptionEntity> getListOptionsByFkQuestion(String fk);

    @Query("SELECT * FROM tblOption WHERE fkquestion =:fkQuestion")
    List<OptionEntity> getOptionsByFkQuestion(String fkQuestion);



}
