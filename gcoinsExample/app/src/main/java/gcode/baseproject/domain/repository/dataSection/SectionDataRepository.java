package gcode.baseproject.domain.repository.dataSection;

import java.util.List;
import java.util.concurrent.ExecutionException;

import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.data.SectionDataDao;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;


public class SectionDataRepository  implements  ISectionDataRepository{

    private SectionDataDao sectionDataDao;
    public SectionDataRepository(){
        sectionDataDao = ApplicationDatabase.getDatabase().getSectionDataDao();
    }


    @Override
    public Completable AddSectionDataDB(final SectionDataEntity sectionDataEntity) {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                sectionDataDao.AddSectionData(sectionDataEntity);
            }
        });
    }

    @Override
    public Single<List<SectionDataEntity>> getAllById(String id) {
        return sectionDataDao.getAll(id);
    }

    @Override
    public Boolean CheckIfExistsData(String id, int folio) {
        GetCountSectionAsyncTask task = new GetCountSectionAsyncTask(sectionDataDao,id,folio);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getCount()>0;
    }


    @Override
    public Thread UpdateSectionDataDB(final SectionDataEntity sectionDataEntity) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                sectionDataDao.UpdateSectionData(sectionDataEntity);
            }
        });
    }



    @Override
    public Integer getCountSectionData(String fk) {
        GetCountSectionDataByFkSectionAsyncTask task = new GetCountSectionDataByFkSectionAsyncTask(sectionDataDao,fk);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getCountSectionData();
    }


}
