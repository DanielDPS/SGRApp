package gcode.baseproject.domain.repository.dataSection;

import android.os.AsyncTask;

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
    public Single<List<SectionDataEntity>> getAllById(String id, String fk) {
        return sectionDataDao.getAll(id,fk);
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
    public Single<Integer> getCountSectionData(String fk, String fkFormatData) {
        return sectionDataDao.getCountSectionDataByFkSection(fk,fkFormatData);
    }


    @Override
    public List<SectionDataEntity> getListSectionsById(String fk) {
       //return sectionDataDao.getListSectionsByIdSection(fk);
        return null;
    }

    @Override
    public Integer getCountSDById(String fk, String id) {
        GetCountSectionDataByFkSectionAsyncTask task = new GetCountSectionDataByFkSectionAsyncTask(sectionDataDao,fk,id);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getCountSectionData();
    }


    @Override
    public SectionDataEntity getObjectSectionById(String fk) {
        GetObjectSectionDataByFkSectonAsyncTask task = new GetObjectSectionDataByFkSectonAsyncTask(sectionDataDao,fk);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getSectionDataEntity();
    }

    @Override
    public Integer getCOUNTForSection(String fk, String idSectionData) {
        GetCountForSectionAsyncTask task= new GetCountForSectionAsyncTask(sectionDataDao,fk,idSectionData);

        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getCount();
    }


    @Override
    public Integer getCountSectionDataById(String id) {
        GetCountSectionDataByIdAyncTask task = new GetCountSectionDataByIdAyncTask(sectionDataDao,id);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getCountSection();
    }

    @Override
    public List<SectionDataEntity> getListSectionDataById(String id) {
        GetListSectionDataByIdAsyncTask task = new GetListSectionDataByIdAsyncTask(sectionDataDao,id);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getSection();
    }

    @Override
    public SectionDataEntity getObjectSectionDataByID(String id) {
        GetObjectSectionDataByIdAsyncTask task = new GetObjectSectionDataByIdAsyncTask(sectionDataDao,id);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getSection();
    }

    @Override
    public List<SectionDataEntity> getListSectionsByFkFormatData(String fkFormatData) {
        GetListSectionByFkFormatDataAsyncTask task = new GetListSectionByFkFormatDataAsyncTask(sectionDataDao,fkFormatData);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getList();

    }

    @Override
    public Completable DeleteAllSecionList(final List<SectionDataEntity> listSectionData) {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                sectionDataDao.DeleteSectionDataList(listSectionData);
            }
        });
    }

    @Override
    public Integer getLastFolio(String fkSection, String fkFormatData) {
        GetLastFolioAsyncTask task = new GetLastFolioAsyncTask(sectionDataDao,fkSection,fkFormatData);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getLastFolio();
    }

    @Override
    public String getId(int folio) {
        GetIdSectionAsyncTask task = new GetIdSectionAsyncTask(sectionDataDao,folio);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getId();
    }


}
