package gcode.baseproject.domain.repository.dataEvidence;

import android.os.AsyncTask;

import gcode.baseproject.domain.model.data.EvidenceData;
import gcode.baseproject.interactors.db.dao.data.EvidenceDataDao;

public class GetCountEvidenceAsyncTask extends AsyncTask<Void,Void,Integer> {

    private EvidenceDataDao evidenceDataDaoAT;
    private  Integer countEvidence;
    private  String fkQuestion;
    public GetCountEvidenceAsyncTask(EvidenceDataDao dao,String fk){
        this.evidenceDataDaoAT = dao;
        this.fkQuestion=fk;

    }
    public  Integer getCount(){
        return this.countEvidence;
    }
    @Override
    protected Integer doInBackground(Void... voids) {
        countEvidence = this.evidenceDataDaoAT.getCountEvidence(fkQuestion);
        return countEvidence;
    }
}
