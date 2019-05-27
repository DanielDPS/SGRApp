package gcode.baseproject.view.viewmodel.format;

import android.app.Application;
import android.util.Log;
import java.util.List;
import java.util.concurrent.ExecutionException;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.model.format.Format;
import gcode.baseproject.domain.model.formatSection.FormatSection;
import gcode.baseproject.domain.model.option.Option;
import gcode.baseproject.domain.model.question.Question;
import gcode.baseproject.domain.repository.format.FormatRepository;
import gcode.baseproject.domain.repository.format.IFormatRepository;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FormatViewModel extends BaseNetworkViewModel {

    private  MutableLiveData<List<FormatSectionEntity>>getFormatSectionEntity =new  MutableLiveData<>();
    private   MutableLiveData<List<QuestionEntity>> getQuestionsDB = new MutableLiveData<>();
    private IFormatRepository iFormatRepository;
     private  String update;
    public FormatViewModel(@NonNull Application application) {
        super(application);
        iFormatRepository = new FormatRepository();
      }
    public Single<List<Format>> getFormatsFromAPI (){
        Single<List<Format>> formats = getSessionManager().getToken()
                .flatMap(new Function<String, SingleSource<List<Format>>>() {
                    @Override
                    public SingleSource<List<Format>> apply(String token) throws Exception {
                        update = iFormatRepository.getFormatLastUpdate();
                        return iFormatRepository.getAllFormatsAndSections(token,update);
                    }
                });
        return formats;

    }
    public FormatSectionEntity getObjectSectionById(String id){
        return  iFormatRepository.getObjectSectioById(id);
    }

    public Integer getQuestionsCountByIdSection(String id){
        return iFormatRepository.getCountQuestionsByFkSection(id);
    }
    public Single<List<Question>> getQuestions(final String sectionId){
        Single<List<Question>> questions  = getSessionManager().getToken()
                .flatMap(new Function<String, SingleSource< List<Question>>>() {
                    @Override
                    public SingleSource< List<Question>> apply(String token) throws Exception {
                        return iFormatRepository.getAllQuestionsAndOptions(token,update,sectionId);
                    }
                });
        return questions;
    }
    public  List<FormatSectionEntity> getSectionsByFkFormat(String fk){
        return  iFormatRepository.getListSectionByFkFormat(fk);
    }
    public  List<FormatEntity> getFormatsDB(){
        return iFormatRepository.getFormatsDB();
    }

    public  FormatEntity getObjectFormatByIdFormat(String id){
        return  iFormatRepository.getObjectFormatById(id);
    }

    public Completable UpdateFormats(){
        Single<List<Format> > formatsJSON = getFormatsFromAPI();

        Completable refreshFormats = formatsJSON.flatMapCompletable(new Function<List<Format>, CompletableSource>() {
            @Override
            public CompletableSource apply(final List<Format> formats) throws Exception {
                return Completable.fromRunnable(new Runnable() {
                    @Override
                    public void run() {

                        FormatEntity formatEntity= null;
                        for (Format format : formats){

                            formatEntity= new FormatEntity();
                            formatEntity.setCId(format.getId());
                            formatEntity.setCDescription(format.getDescription());
                            formatEntity.setCEdited(format.getUpgrade());
                            formatEntity.setCActive(format.getActivo());
                            formatEntity.setcSections(format.getSections());

                            if (iFormatRepository.checkIfFormatsExists(formatEntity.getCId())){

                                if (iFormatRepository.checkIfFormatsUpdate(formatEntity.getCId(),update)){
                                    iFormatRepository.UpdateFormatDB(formatEntity);
                                 }
                            }else {
                                iFormatRepository.AddFormatDB(formatEntity);

                            }

                        }
                         for (FormatSection formatSection : formatEntity.getcSections()){

                            FormatSectionEntity formatSectionEntity=new FormatSectionEntity();
                            formatSectionEntity.setId(formatSection.getId());
                            formatSectionEntity.setFkformat(formatEntity.getCId());
                            formatSectionEntity.setDescription(formatSection.getDescription());
                            formatSectionEntity.setMultipledescription(formatSection.getMultipleDescription());
                            formatSectionEntity.setIsmultipleref(formatSection.getIsMultipleReference());
                            formatSectionEntity.setEdited(formatSection.getcEdited());
                            formatSectionEntity.setOrder(formatSection.getOrder());
                            formatSectionEntity.setImage(formatSection.getcImage());

                            if (iFormatRepository.checkIfFormatSectionsExists(formatSectionEntity.getId())){
                                iFormatRepository.UpdateFormatSectionDB(formatSectionEntity);

                                try {
                                    formatSectionEntity.setcQuestions(getQuestions(formatSection.getId()).toFuture().get());

                                    for (Question question :formatSectionEntity.getcQuestions()){
                                        QuestionEntity questionEntity= new QuestionEntity();
                                        questionEntity.setId(question.getId());
                                        questionEntity.setFksection(formatSectionEntity.getId());
                                        questionEntity.setDescription(question.getDescription());
                                        questionEntity.setHallazgo(question.getHallazgo());
                                        questionEntity.setFundament(question.getFundament());
                                        questionEntity.setCritical(question.isCritical());
                                        questionEntity.setFieldtype(question.getFieldType());
                                        questionEntity.setOrder(0);
                                        questionEntity.setImage(question.getImage());
                                        questionEntity.setEdited(question.getEdited());
                                        iFormatRepository.UpdateQuestionDB(questionEntity);
                                        questionEntity.setOptionsList(question.getOptions());
                                        for(Option option :questionEntity.getOptionsList()){
                                            OptionEntity optionEntity = new OptionEntity();
                                            optionEntity.setId(option.getId());
                                            optionEntity.setFkquestion(questionEntity.getId());
                                            optionEntity.setDescription(option.getDescription());
                                            iFormatRepository.UpdateOptionDB(optionEntity);
                                        }

                                    }


                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }else {
                                iFormatRepository.AddFormatSectionDB(formatSectionEntity);
                                try {
                                    formatSectionEntity.setcQuestions(getQuestions(formatSection.getId()).toFuture().get());
                                     for (Question question :formatSectionEntity.getcQuestions()){
                                        QuestionEntity questionEntity= new QuestionEntity();
                                        questionEntity.setId(question.getId());
                                        questionEntity.setFksection(formatSectionEntity.getId());
                                        questionEntity.setDescription(question.getDescription());
                                        questionEntity.setHallazgo(question.getHallazgo());
                                        questionEntity.setFundament(question.getFundament());
                                        questionEntity.setCritical(question.isCritical());
                                        questionEntity.setFieldtype(question.getFieldType());
                                        questionEntity.setOrder(0);
                                        questionEntity.setImage(question.getImage());
                                        questionEntity.setEdited(question.getEdited());
                                        iFormatRepository.AddQuestionDB(questionEntity);
                                        questionEntity.setOptionsList(question.getOptions());
                                        for(Option option :questionEntity.getOptionsList()){
                                            OptionEntity optionEntity = new OptionEntity();
                                            optionEntity.setId(option.getId());
                                            optionEntity.setFkquestion(questionEntity.getId());
                                            optionEntity.setDescription(option.getDescription());
                                            iFormatRepository.AddOptionDB(optionEntity);
                                        }

                                    }


                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }



                            }

                        }

                    }
                });
            }
        });

        return  refreshFormats;

    }
    public  void ClearSectionsByFkFormat(LifecycleOwner owner){
        if (getFormatSectionEntity != null){
            getFormatSectionEntity.removeObservers(owner);
            getFormatSectionEntity = null;
        }
    }

    public void LoadSections(String idformat){
        if (getFormatSectionEntity == null){

            getFormatSectionEntity= new MutableLiveData<>();
        }
            Single<List<FormatSectionEntity> > entities = iFormatRepository.getFomatSectionsDB(idformat)
                    .map(new Function<List<FormatSectionEntity>, List<FormatSectionEntity>>() {
                        @Override
                        public List<FormatSectionEntity> apply(List<FormatSectionEntity> formatSectionEntities) throws Exception {
                            return formatSectionEntities;
                        }
                    });
            entities.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObservableFormatSections());

    }
    public void ClearQuestions(LifecycleOwner owner){
        if (getQuestionsDB != null){
            getQuestionsDB.removeObservers(owner);
            getQuestionsDB= null;
        }
    }
    public void LoadQuestionsDB(String id){
        if (getQuestionsDB== null){
            getQuestionsDB= new MutableLiveData<>();
        }
            Single<List<QuestionEntity>> questions = iFormatRepository.getQuestionsByIdSection(id)
                    .map(new Function<List<QuestionEntity>, List<QuestionEntity>>() {
                        @Override
                        public List<QuestionEntity> apply(List<QuestionEntity> questionEntities) throws Exception {
                            return questionEntities;
                        }
                    });
            questions.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObservableQuestionsDB());



    }
    private  DisposableSingleObserver<List<QuestionEntity>> getObservableQuestionsDB(){
        return new DisposableSingleObserver<List<QuestionEntity>>() {
            @Override
            public void onSuccess(List<QuestionEntity> questionEntities) {
                getQuestionsDB.setValue(questionEntities);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e("TAG5",e.getLocalizedMessage());
            }
        };
    }
    public List<OptionEntity> getOptionsListByFkQuestion(String fk){
        return  iFormatRepository.getOptionsByFKQuestion(fk);
    }
    public  List<QuestionEntity> getQuestionsListByFkSection(String id){
        return  iFormatRepository.getListQuestionsByFkSection(id);
    }
    private DisposableSingleObserver<List<FormatSectionEntity>> getObservableFormatSections() {
        return new DisposableSingleObserver<List<FormatSectionEntity>>() {

            @Override
            public void onSuccess(List<FormatSectionEntity> formatSectionEntities) {
                getFormatSectionEntity.postValue(formatSectionEntities);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e("TAG1",e.getLocalizedMessage());
            }
        };
    }
    public    MutableLiveData<List<FormatSectionEntity>> mgetFormatSections(){
        return getFormatSectionEntity;
    }

    public MutableLiveData<List<QuestionEntity>>mgetQuestionsDB(){
        return getQuestionsDB;
    }

}
