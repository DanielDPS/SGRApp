package gcode.baseproject.domain.repository.dataFormat;

import android.os.AsyncTask;
import android.print.PrinterId;

import java.util.List;
import java.util.concurrent.ExecutionException;

import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.data.FormatDataDao;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.view.ui.format.FormatDataModuleFragment;
import gcode.baseproject.view.ui.format.FormatSectionsFragment;
import io.reactivex.Completable;
import io.reactivex.Single;

public class FormatDataRepository implements  IFormatDataRepository {

    private FormatDataDao formatDataDao;
    public  FormatDataRepository(){
        formatDataDao = ApplicationDatabase.getDatabase().getFormatDataDao();
    }
    @Override
    public Completable AddFormatData(final FormatDataEntity formatDataEntity) {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                //if (formatDataDao.checkIfExists(formatDataEntity.getFkformat(),formatDataEntity.getFkCustomer())>0){
                  //  formatDataDao.UpdateFormatDat(formatDataEntity);
                //}else {
                    formatDataDao.AddFormatData(formatDataEntity);
                //}
            }
        });
    }

    @Override
    public Completable UpdateFormatData(final FormatDataEntity formatDataEntity) {
        return  Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                formatDataDao.UpdateFormatDat(formatDataEntity);
            }
        });
    }

    @Override
    public Completable DeleteFormatData(final FormatDataEntity formatDataEntity) {

        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                formatDataDao.DeleteFormatData(formatDataEntity);

            }
        });


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

    @Override
    public Integer checkIfExistsObject(String fkformat, String fkCustomer, String identifier) {
        GetCountFormatDataByIdFormatAndCustomerAsyncTask task = new GetCountFormatDataByIdFormatAndCustomerAsyncTask(formatDataDao,fkformat,fkCustomer,identifier);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getCount();
    }


    @Override
    public FormatDataEntity getFormatDataById(String id) {
        GetFormatDataByIdAsyncTask task= new GetFormatDataByIdAsyncTask(formatDataDao,id);

        try {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getFormatDataEntity();
    }

    @Override
    public void  UpdateFormat(final int state01,final  int state02,final String id) {

               //formatDataDao.UpdateFormat(state01,state02,id);

    }


}
