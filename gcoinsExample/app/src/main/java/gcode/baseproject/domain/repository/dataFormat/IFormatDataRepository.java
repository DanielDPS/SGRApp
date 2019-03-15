package gcode.baseproject.domain.repository.dataFormat;

import java.util.List;

import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface IFormatDataRepository {

    void  AddFormatData(FormatDataEntity formatDataEntity);
    void UpdateFormatData(FormatDataEntity formatDataEntity);
    Single<List<FormatDataEntity>> getAllFormatsData();
    Boolean getCount();
    Boolean getIdFormatData( );
 }
