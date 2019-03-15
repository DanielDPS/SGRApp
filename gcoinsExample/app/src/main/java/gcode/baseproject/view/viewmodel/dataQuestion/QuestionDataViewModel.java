package gcode.baseproject.view.viewmodel.dataQuestion;

import android.app.Application;
import android.print.PrinterId;

import androidx.annotation.NonNull;
import gcode.baseproject.domain.repository.dataQuestion.IQuestionDataRepository;
import gcode.baseproject.domain.repository.dataQuestion.QuestionDataRepository;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class QuestionDataViewModel extends BaseNetworkViewModel {


    private IQuestionDataRepository iQuestionDataRepository;
    public QuestionDataViewModel(@NonNull Application application) {
        super(application);
        iQuestionDataRepository = new QuestionDataRepository();
    }

    public  void AddQuestionData(QuestionDataEntity questionDataEntity){
        TestObserver testObserver = new TestObserver();
        iQuestionDataRepository.AddQuestionData(questionDataEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(testObserver);
        testObserver.assertNoErrors();
    }
    public Integer getCountAnswersByFkSectionData(String fkQuestion){
        return iQuestionDataRepository.getCountAnswersById(fkQuestion);
    }

    public Integer getCountQuestionsByFkQuestion(String fkQuestion){
        return iQuestionDataRepository.getCountQuestionsDataByFkQuestion(fkQuestion);
    }



}
