package gcode.baseproject.interactors.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import gcode.baseproject.interactors.db.entities.TokenEntity;
import io.reactivex.Single;

@Dao
public interface TokenDao {
    @Insert
    void add(TokenEntity token);

    @Query("SELECT data FROM token LIMIT 1")
    Single<String> getToken();

    @Query("DELETE FROM Token")
    void remove();

    @Query("SELECT COUNT(*) FROM Token")
    Single<Integer> getCount();
}
