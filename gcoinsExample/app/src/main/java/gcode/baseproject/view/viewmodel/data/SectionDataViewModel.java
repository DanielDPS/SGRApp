package gcode.baseproject.view.viewmodel.data;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.repository.dataSection.ISectionDataRepository;
import gcode.baseproject.domain.repository.dataSection.SectionDataRepository;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class SectionDataViewModel extends BaseNetworkViewModel {

    private MutableLiveData<List<SectionDataEntity>> getSectionsData= new MutableLiveData<>();
    private ISectionDataRepository iSectionDataRepository;
    public SectionDataViewModel(@NonNull Application application) {
        super(application);
        iSectionDataRepository= new SectionDataRepository();
    }
    public  Boolean CheckIfExists(String id,int folio){
        return iSectionDataRepository.CheckIfExistsData(id,folio);
    }


    public Integer getCountSectionDataByFkSection(String fk){
        return iSectionDataRepository.getCountSectionData(fk);
    }



    public void LoadSectoionsData(String id){
        if (getSectionsData.getValue()==null){
            Single<List<SectionDataEntity>> sections =
                    iSectionDataRepository.getAllById(id)
                    .map(new Function<List<SectionDataEntity>, List<SectionDataEntity>>() {
                        @Override
                        public List<SectionDataEntity> apply(List<SectionDataEntity> sectionDataEntities) throws Exception {
                            return sectionDataEntities;
                        }
                    });
            sections.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserverData());
        }

    }

    public void AddSectionData(SectionDataEntity sectionDataEntity){
        TestObserver testObserver=new TestObserver();
        iSectionDataRepository.AddSectionDataDB(sectionDataEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(testObserver);
        testObserver.assertNoErrors();
    }
    public void UpdateSecitonData(SectionDataEntity sectionDataEntity){
         iSectionDataRepository.UpdateSectionDataDB(sectionDataEntity).start();
    }



    private DisposableSingleObserver<List<SectionDataEntity>> getObserverData(){
        return new DisposableSingleObserver<List<SectionDataEntity>>() {
            @Override
            public void onSuccess(List<SectionDataEntity> sectionDataEntities) {
                getSectionsData.setValue(sectionDataEntities);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();


            }
        };
    }

    public  MutableLiveData<List<SectionDataEntity>> getData(){
        return  getSectionsData;
    }


}
