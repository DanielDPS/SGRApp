package gcode.baseproject.domain.repository.option;

import java.util.List;

import gcode.baseproject.interactors.db.entities.OptionEntity;
import io.reactivex.Single;

public interface IOptionRepository {

    Single<List<OptionEntity>> getOptionByIdQuestion(String id);
}
