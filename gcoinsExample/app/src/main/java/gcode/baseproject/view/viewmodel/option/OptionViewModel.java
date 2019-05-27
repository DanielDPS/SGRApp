package gcode.baseproject.view.viewmodel.option;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.repository.option.IOptionRepository;
import gcode.baseproject.domain.repository.option.OptionRepository;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class OptionViewModel  extends BaseNetworkViewModel {


    private MutableLiveData<List<OptionEntity>> mgetOptions = new MutableLiveData<>();
    private IOptionRepository iOptionRepository;

    public OptionViewModel(@NonNull Application application) {
        super(application);
        iOptionRepository = new OptionRepository();
    }

    public  OptionEntity getOptionById(String id){
        return iOptionRepository.getOptionEntity(id);
    }
    public void ClearOptions (LifecycleOwner owner){
        if (mgetOptions != null){
            mgetOptions.removeObservers(owner);
            mgetOptions =null;
        }
    }

    public void LoadOptionsByIdQuestion(String id){

        if (mgetOptions == null ){

            mgetOptions = new MutableLiveData<>();
        }
            Single<List<OptionEntity>> options = iOptionRepository.getOptionByIdQuestion(id)
                    .map(new Function<List<OptionEntity>, List<OptionEntity>>() {
                        @Override
                        public List<OptionEntity> apply(List<OptionEntity> optionEntities) throws Exception {
                            return optionEntities;
                        }
                    });
            options.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserverOptions());

    }
    private DisposableSingleObserver<List<OptionEntity>> getObserverOptions() {
        return new DisposableSingleObserver<List<OptionEntity>>() {
            @Override
            public void onSuccess(List<OptionEntity> optionEntities) {
                mgetOptions.setValue(optionEntities);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

            }
        };
    }
    public MutableLiveData<List<OptionEntity>> mgetOptionsEntity (){
        return mgetOptions;
    }





}
