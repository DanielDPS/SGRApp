package gcode.baseproject.view.viewmodel.dataEvidence;

import android.app.Application;
import android.util.Log;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.repository.dataEvidence.EvidenceDataRepository;
import gcode.baseproject.domain.repository.dataEvidence.IEvidenceDataRepository;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class EvidenceDataViewModel extends BaseNetworkViewModel {

    private MutableLiveData<List<EvidenceDataEntity> > getDataEvidence= new MutableLiveData<>();
    private IEvidenceDataRepository iEvidenceDataRepository;
    public EvidenceDataViewModel(@NonNull Application application) {
        super(application);
        iEvidenceDataRepository = new EvidenceDataRepository();
    }
    public  Completable UpdateEvidence(EvidenceDataEntity evidenceDataEntity){
        return  iEvidenceDataRepository.UpdateEvidence(evidenceDataEntity);
    }

    public  List<EvidenceDataEntity> getListEvidenceByFkQuestionData(String fkQuestionData){
        return  iEvidenceDataRepository.getEvidenceListByFkQuestionData(fkQuestionData);
    }

    public Completable AddEvidence(EvidenceDataEntity evidenceDataEntity){
       return   iEvidenceDataRepository.addEvidenceData(evidenceDataEntity);
    }
    public Completable DeleteEvidence(EvidenceDataEntity evidenceDataEntity){
        return iEvidenceDataRepository.DeleteEvidence(evidenceDataEntity);
    }



    public Integer getCountEvidence (String fk){

        return iEvidenceDataRepository.getCountEvidenceData(fk);
    }
    public void ClearEvidenceList(LifecycleOwner owner){
        if (getDataEvidence != null){
            getDataEvidence.removeObservers(owner);
            getDataEvidence = null;
        }
    }
    public void LoadEvidenceList(String id){
        if (getDataEvidence == null){
            getDataEvidence = new MutableLiveData<>();
        }
           Single<List<EvidenceDataEntity>> list =  iEvidenceDataRepository.getEvidenceList(id)
                   .map(new Function<List<EvidenceDataEntity>, List<EvidenceDataEntity>>() {
                       @Override
                       public List<EvidenceDataEntity> apply(List<EvidenceDataEntity> evidenceDataEntities) throws Exception {
                           return evidenceDataEntities;
                       }
                   });
           list.subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(getDisposableEvidence());


    }


    private DisposableSingleObserver<List<EvidenceDataEntity>> getDisposableEvidence(){
        return new DisposableSingleObserver<List<EvidenceDataEntity>>() {
            @Override
            public void onSuccess(List<EvidenceDataEntity> evidenceDataEntities) {
                getDataEvidence.postValue(evidenceDataEntities);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e("TAG2",e.getLocalizedMessage());
            }
        };
    }
    public  MutableLiveData<List<EvidenceDataEntity>> getEvidenceData(){
        return getDataEvidence;

    }
}
