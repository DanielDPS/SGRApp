package gcode.baseproject.domain.repository.dataEvidence;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutionException;

import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.data.EvidenceDataDao;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public class EvidenceDataRepository implements IEvidenceDataRepository {

    private EvidenceDataDao evidenceDataDao;
    public  EvidenceDataRepository(){
        evidenceDataDao = ApplicationDatabase.getDatabase().getEvidenceDataDao();
    }
    @Override
    public Completable addEvidenceData(final EvidenceDataEntity evidenceDataEntity) {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {

                    evidenceDataDao.AddEvidence(evidenceDataEntity);
            }
        });
    }

    @Override
    public Integer getCountEvidenceData(String fkquestionData) {

        GetCountEvidenceAsyncTask task = new GetCountEvidenceAsyncTask(evidenceDataDao,fkquestionData);

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
    public Single<List<EvidenceDataEntity>> getEvidenceList(String fk) {
        return evidenceDataDao.getEvidence(fk);
    }

    @Override
    public Completable DeleteEvidence(final EvidenceDataEntity evidenceDataEntity) {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
             evidenceDataDao.DeleteEvidence(evidenceDataEntity);
            }
        });
    }

    @Override
    public Completable UpdateEvidence(final EvidenceDataEntity evidenceDataEntity) {
        return  Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                evidenceDataDao.UpdateEvidence(evidenceDataEntity);
            }
        });
    }

    @Override
    public List<EvidenceDataEntity> getEvidenceListByFkQuestionData(String fk) {
        GetListEvidenceByFkQuestionDataAsyncTask task = new GetListEvidenceByFkQuestionDataAsyncTask(evidenceDataDao,fk);

        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getList();

    }
}
