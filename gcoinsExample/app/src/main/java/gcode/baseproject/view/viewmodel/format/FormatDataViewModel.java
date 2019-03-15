package gcode.baseproject.view.viewmodel.format;

import android.app.Application;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.repository.dataFormat.FormatDataRepository;
import gcode.baseproject.domain.repository.dataFormat.IFormatDataRepository;
import gcode.baseproject.interactors.date.CurrentDate;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.session.SessionUser;
import gcode.baseproject.view.ui.format.FormatSectionsFragment;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class FormatDataViewModel extends BaseNetworkViewModel {

    private MutableLiveData<List<FormatDataEntity>> getFormatData = new MutableLiveData<>();
    private IFormatDataRepository iFormatDataRepository;
    public FormatDataViewModel(@NonNull Application application) {
        super(application);
        iFormatDataRepository= new FormatDataRepository();
    }
    public void Update(FormatDataEntity formatDataEntity){
        iFormatDataRepository.UpdateFormatData(formatDataEntity);
    }


    public Boolean getCount(){
        return iFormatDataRepository.getCount();
    }
    public  Boolean getIdFormat(){
        return iFormatDataRepository.getIdFormatData();
    }

    public void AddFormatDataActions(FormatDataEntity formatDataEntity){
        iFormatDataRepository.AddFormatData(formatDataEntity);
    }


     public void ClearFormatData(LifecycleOwner owner){
        if (getFormatData != null){
            getFormatData.removeObservers(owner);
            getFormatData =null;
        }
    }

    public void LoadFormatData(){
        if (getFormatData== null){
            getFormatData =new MutableLiveData<>();
        }
        Single<List<FormatDataEntity>> list =iFormatDataRepository.getAllFormatsData()
                .map(new Function<List<FormatDataEntity>, List<FormatDataEntity>>() {
                    @Override
                    public List<FormatDataEntity> apply(List<FormatDataEntity> formatDataEntities) throws Exception {
                        return formatDataEntities;
                    }
                });
        list.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSingle());
    }
    private DisposableSingleObserver<List<FormatDataEntity>> getSingle(){
        return new DisposableSingleObserver<List<FormatDataEntity>>() {
            @Override
            public void onSuccess(List<FormatDataEntity> formatDataEntities) {
             getFormatData.postValue(formatDataEntities);
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e("TAG6",e.getLocalizedMessage());
            }
        };
    }

    public MutableLiveData<List<FormatDataEntity>> getGetFormatData() {
        return getFormatData;
    }
}
