package gcode.baseproject.view.viewmodel.data;

import android.app.Application;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.repository.dataSection.ISectionDataRepository;
import gcode.baseproject.domain.repository.dataSection.SectionDataRepository;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class SectionDataViewModel extends BaseNetworkViewModel {

    private MutableLiveData<List<SectionDataEntity>> getSectionsData= new MutableLiveData<>();
    private  MutableLiveData<Integer> countSections = new MutableLiveData<>();
    private ISectionDataRepository iSectionDataRepository;
    public SectionDataViewModel(@NonNull Application application) {
        super(application);
        iSectionDataRepository= new SectionDataRepository();
    }
    public  Integer getCountSectionDataByID(String id){
        return  iSectionDataRepository.getCountSectionDataById(id);
    }
    public  Boolean CheckIfExists(String id,int folio){
        return iSectionDataRepository.CheckIfExistsData(id,folio);
    }

    public  List<SectionDataEntity> getListSectionsByFkSection(String fk){
        //return  iSectionDataRepository.getListSectionsById(fk);
        return null;
    }

    public  void ClearCountSections (LifecycleOwner owner){
        if (countSections != null){
            countSections.removeObservers(owner);
            countSections  = null;
        }
    }
    public void  LoadCountSections(String fk,String fkFormatData){
        if (countSections == null){
            countSections = new MutableLiveData<>();
        }
        Single<Integer> countSections= iSectionDataRepository.getCountSectionData(fk,fkFormatData)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer;
                    }
                });
        countSections.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObservableCount());
    }
    private  DisposableSingleObserver<Integer> getObservableCount(){
        return new DisposableSingleObserver<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                countSections.postValue(integer);
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e("TAG3",e.getLocalizedMessage());

            }
        };
    }
    public  List<SectionDataEntity> getListSectionDataById(String id){
        return  iSectionDataRepository.getListSectionsById(id);
    }
    public  SectionDataEntity getObjectSectionDataByFkSection(String fk){
        return  iSectionDataRepository.getObjectSectionById(fk);
    }
    public  SectionDataEntity getObjectSectionDataByID(String id)
    {
        return  iSectionDataRepository.getObjectSectionDataByID(id);
    }
    public  MutableLiveData<Integer> getCountSections(){
        return this.countSections;
    }
    public Integer getCountSDByFkSection(String fk,String id){
        return iSectionDataRepository.getCountSDById(fk,id);
    }


    public  void ClearSectionData(LifecycleOwner owner){
        if (getSectionsData != null)
        {
            getSectionsData.removeObservers(owner);
            getSectionsData = null;
        }
    }
    public void LoadSectoionsData(String id,String fkf){
        if (getSectionsData ==null){

            getSectionsData = new MutableLiveData<>();
        }
            Single<List<SectionDataEntity>> sections =
                    iSectionDataRepository.getAllById(id,fkf)
                    .map(new Function<List<SectionDataEntity>, List<SectionDataEntity>>() {
                        @Override
                        public List<SectionDataEntity> apply(List<SectionDataEntity> sectionDataEntities) throws Exception {
                            return sectionDataEntities;
                        }
                    });
            sections.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserverData());


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
                getSectionsData.postValue(sectionDataEntities);
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


    public  Integer getCOUNTForSectionId(String id,String idSectionData){
        return  iSectionDataRepository.getCOUNTForSection(id,idSectionData);
    }

    public  List<SectionDataEntity> getListSectionsByFkFormatData(String fkFormatData){
        return  iSectionDataRepository.getListSectionsByFkFormatData(fkFormatData);
    }
    public Completable DeleteListSectionData (List<SectionDataEntity> listSectionData){
        return  iSectionDataRepository.DeleteAllSecionList(listSectionData);
    }

    public  Integer getLastFolioMultiple(String idSection,String idFormatData){
        return  iSectionDataRepository.getLastFolio(idSection,idFormatData);
    }
    public  String getIdSectionDataByFolio(int folio){
        return  iSectionDataRepository.getId(folio);
    }


}
