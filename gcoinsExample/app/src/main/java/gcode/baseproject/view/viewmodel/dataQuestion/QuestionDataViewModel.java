package gcode.baseproject.view.viewmodel.dataQuestion;

import android.app.Application;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.repository.dataQuestion.IQuestionDataRepository;
import gcode.baseproject.domain.repository.dataQuestion.QuestionDataRepository;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class QuestionDataViewModel extends BaseNetworkViewModel {


    private IQuestionDataRepository iQuestionDataRepository;
    public QuestionDataViewModel(@NonNull Application application) {
        super(application);
        iQuestionDataRepository = new QuestionDataRepository();
    }
//getQuestionDataObejctByIdQuestion

    public void UpdateQuestionData(QuestionDataEntity questionDataEntity){
        TestObserver testObserver = new TestObserver();
        iQuestionDataRepository.UpdateQuestionData(questionDataEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(testObserver);
        testObserver.assertNoErrors();
    }
    public  Completable  AddQuestionData(final QuestionDataEntity questionDataEntity,final FormatSectionEntity section){

        return  Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                        if (section.getMultipledescription().equals("")){
                            if (iQuestionDataRepository.getCountExistsNormales(questionDataEntity.getFkquestion(),questionDataEntity.getFkformatData())>0){

                                iQuestionDataRepository.UpdateQuestion(questionDataEntity);

                            }else {
                                iQuestionDataRepository.AddQuestionData(questionDataEntity);

                            }
                        }else {
                            if (iQuestionDataRepository.getCountExists(questionDataEntity.getFkquestion(),questionDataEntity.getFkformatData(),questionDataEntity.getFksectionData())>0){
                                iQuestionDataRepository.UpdateQuestion(questionDataEntity);
                            }else {
                                iQuestionDataRepository.AddQuestionData(questionDataEntity);
                            }
                        }











            }
        });
    }
    public  Integer  getTotalAnswersByIds(String idFormat,String idSectionData){
        return  iQuestionDataRepository.getTotalAnswers(idFormat,idSectionData);
    }

    public Integer getCountAnswersByFkSectionData(String fkQuestion,String fkFormatData){
        return iQuestionDataRepository.getCountAnswersById(fkQuestion,fkFormatData);
    }

    public QuestionDataEntity getCountQuestionsByFkQuestion(String fkQuestion,String fkFormatData){
        return iQuestionDataRepository.getCountQuestionsDataByFkQuestion(fkQuestion,fkFormatData);
    }
    public  QuestionDataEntity getObjectQuestionByFkQuestion(String fkQuestion,String fkFormatData,String fkSectionData){
        return iQuestionDataRepository.getQuestionDataObejctByIdQuestion(fkQuestion,fkFormatData,fkSectionData);
    }

    //preguntas multiples
    public  Integer getCountQuestionsMultipleByIds(String fkQuestion,String fkFormatData,String fkSectionData){
        return  iQuestionDataRepository.getCountQuestionsMultiple(fkQuestion,fkFormatData,fkSectionData);
    }

    //ANSWERS LIST ASYNCTASK
    public List<QuestionDataEntity > getAnswersByFkQuestion(String fkQuestion){
        return  iQuestionDataRepository.getAnswersByFkQuestion(fkQuestion);
    }



    //MULTIPLE COUNT QUESTIONS
    public  Integer getCOUNTMultipleQuestionsByFks( String fkFormatData,String fkSectionData){
        return  iQuestionDataRepository.getCOUNTMultipleQuestions(fkFormatData,fkSectionData);
    }


    //GET AND DELETE
    public  List<QuestionDataEntity> getAnswersListByFkFormatData(String fkFormatData){
        return  iQuestionDataRepository.getListAnswersByFkFormatData(fkFormatData);
    }
    public Completable DeleteAllAnswerList(List<QuestionDataEntity> listAnswers){
        return  iQuestionDataRepository.DeleteAnswersListByFkFormatData(listAnswers);
    }



    //question multiple for click

    private MutableLiveData<QuestionDataEntity> getQuestionLiveDataMultiple = new MutableLiveData<>();
    public  void LoadQuestionMultiple(String fkQuestion,String fkFormatData,String fkSectionData){

        Single<QuestionDataEntity>  quesitonMultiple = iQuestionDataRepository.getAnswersMultiple(fkQuestion,fkFormatData,fkSectionData)
                .map(new Function<QuestionDataEntity, QuestionDataEntity>() {
                    @Override
                    public QuestionDataEntity apply(QuestionDataEntity questionDataEntity) throws Exception {
                        return questionDataEntity;
                    }
                });
        quesitonMultiple.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObservableMultiple());

    }
    public DisposableSingleObserver<QuestionDataEntity> getObservableMultiple (){
        return  new DisposableSingleObserver<QuestionDataEntity>() {
            @Override
            public void onSuccess(QuestionDataEntity questionDataEntity) {
                getQuestionLiveDataMultiple.postValue(questionDataEntity);
            }

            @Override
            public void onError(Throwable e) {
             e.printStackTrace();
            }
        };
    }
    public MutableLiveData<QuestionDataEntity> getGetQuestionMuliple() {
        return getQuestionLiveDataMultiple;
    }

    //question multiple for swipe
    private  MutableLiveData<QuestionDataEntity> getQuestionMultipleSwipe= new MutableLiveData<>();
    public MutableLiveData<QuestionDataEntity> getQuestionMultipleForSwipe(){
        return  getQuestionMultipleSwipe;
    }
    public  void LoadQuestionMultipleSwipe(String fkQuestion,String fkFormatData,String fkSectionData){
        Single<QuestionDataEntity> questionMultipleSwipe = iQuestionDataRepository.getAnswersMultipelSwipe(fkQuestion,fkFormatData,fkSectionData)
                .map(new Function<QuestionDataEntity, QuestionDataEntity>() {
                    @Override
                    public QuestionDataEntity apply(QuestionDataEntity questionMultipleSwipe) throws Exception {
                        return questionMultipleSwipe;
                    }
                });
        questionMultipleSwipe.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObservableQuestionMultipleSwipe());
    }
    private  DisposableSingleObserver<QuestionDataEntity> getObservableQuestionMultipleSwipe(){
        return  new DisposableSingleObserver<QuestionDataEntity>() {
            @Override
            public void onSuccess(QuestionDataEntity questionMultipleSwipe) {
                getQuestionMultipleSwipe.postValue(questionMultipleSwipe);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        };
    }

    //count for multipel swipe
    public  Integer getCountQuestioMultipleForSwipe (String fkQuestion,String fkSectionData){
        return iQuestionDataRepository.getCountQuestionMultipleForSwipe(fkQuestion,fkSectionData);
    }

    public  QuestionDataEntity getObjectAnswerMultiple (String idQuestion,String idFormatData,String idSectionData){
        return  iQuestionDataRepository.getAnswerObjectMultiple(idQuestion,idFormatData,idSectionData);
    }
}
