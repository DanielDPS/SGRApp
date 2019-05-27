package gcode.baseproject.domain.repository.dataFormat;

import java.util.List;

import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface IFormatDataRepository {

    Completable  AddFormatData(FormatDataEntity formatDataEntity);
    Completable UpdateFormatData(FormatDataEntity formatDataEntity);
    Completable DeleteFormatData(FormatDataEntity formatDataEntity);
    Single<List<FormatDataEntity>> getAllFormatsData();
    Boolean getCount();
    Boolean getIdFormatData( );
    Integer checkIfExistsObject(String fkformat,String fkCustomer,String identifier);
    FormatDataEntity getFormatDataById(String id);
    void UpdateFormat(int state01,int state02,String id);

 }
