package gcode.baseproject.domain.repository.option;

import java.util.List;

import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.OptionDao;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import io.reactivex.Single;

public class OptionRepository implements   IOptionRepository {

    private OptionDao optionDao;
    public OptionRepository(){
        optionDao = ApplicationDatabase.getDatabase().getOptionDao();

    }
    @Override
    public Single<List<OptionEntity>> getOptionByIdQuestion(String id) {
        return optionDao.getOptionsByQuestion(id);
    }
}
