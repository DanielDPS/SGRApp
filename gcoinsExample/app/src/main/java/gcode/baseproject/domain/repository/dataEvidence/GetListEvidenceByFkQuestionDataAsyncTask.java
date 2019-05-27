package gcode.baseproject.domain.repository.dataEvidence;

import android.os.AsyncTask;

import java.util.List;

import gcode.baseproject.interactors.db.dao.data.EvidenceDataDao;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;

public class GetListEvidenceByFkQuestionDataAsyncTask extends AsyncTask<Void,Void, List<EvidenceDataEntity>> {

    private  List<EvidenceDataEntity >list;
    private  String fk;
    private EvidenceDataDao evidenceDataDao;
    public  GetListEvidenceByFkQuestionDataAsyncTask(EvidenceDataDao dao,String fk){
        this.evidenceDataDao =dao;
        this.fk= fk;
    }
    public  List<EvidenceDataEntity> getList(){
        return  this.list;
    }
    @Override
    protected List<EvidenceDataEntity> doInBackground(Void... voids) {
        return this.list = evidenceDataDao.getEvidenceListByFkQuestionData(this.fk);
    }
}
