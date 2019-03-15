package gcode.baseproject.domain.repository.dataFormat;

import android.print.PrinterId;

import java.util.List;
import java.util.concurrent.ExecutionException;

import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.data.FormatDataDao;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public class FormatDataRepository implements  IFormatDataRepository {

    private FormatDataDao formatDataDao;
    public  FormatDataRepository(){
        formatDataDao = ApplicationDatabase.getDatabase().getFormatDataDao();
    }
    @Override
    public void AddFormatData(final FormatDataEntity formatDataEntity) {

        formatDataDao.AddFormatData(formatDataEntity);
    }

    @Override
    public void UpdateFormatData(FormatDataEntity formatDataEntity) {
        formatDataDao.UpdateFormatDat(formatDataEntity);
    }

    @Override
    public Single<List<FormatDataEntity>> getAllFormatsData() {
        return formatDataDao.getFormatsData();
    }

    @Override
    public Boolean getCount() {
        return formatDataDao.getCountFormatsData()>0;
    }

    @Override
    public Boolean getIdFormatData( ) {
        return formatDataDao.getIdFormatData() != null;
    }


}
