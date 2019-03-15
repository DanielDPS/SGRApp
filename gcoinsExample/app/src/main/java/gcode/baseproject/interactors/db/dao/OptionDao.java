package gcode.baseproject.interactors.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import io.reactivex.Single;

@Dao
public interface OptionDao {

    @Insert
    void AddOption(OptionEntity optionEntity);
    @Query("SELECT * FROM tblOption WHERE fkquestion = :id")
    Single<List<OptionEntity>>getOptionsByQuestion(String id);
}
