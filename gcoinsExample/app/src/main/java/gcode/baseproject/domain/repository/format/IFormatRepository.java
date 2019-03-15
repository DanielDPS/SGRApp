package gcode.baseproject.domain.repository.format;

import java.util.List;

import gcode.baseproject.domain.model.customer.Customer;
import gcode.baseproject.domain.model.format.Format;
import gcode.baseproject.domain.model.formatSection.FormatSection;
import gcode.baseproject.domain.model.question.Question;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;

public interface IFormatRepository {
    Single<List<Format>> getAllFormatsAndSections(String token,String lastUpdate);
    Single<List<Question>> getAllQuestionsAndOptions(String token,String lastUpdate,String idSection);
    void AddFormatDB(FormatEntity formatEntity);
    void UpdateFormatDB(FormatEntity formatEntity);
    void DeleteFormatDB(FormatEntity formatEntity);
    List<FormatEntity>  getFormatsDB( );
    Boolean checkIfFormatsExists(String id);
    Boolean checkIfFormatsUpdate(String id,String lastUpdate);
    String getFormatLastUpdate();

    //FormatSection
    void AddFormatSectionDB(FormatSectionEntity formatSectionEntity);
    void UpdateFormatSectionDB(FormatSectionEntity formatSectionEntity);
    Single<List<FormatSectionEntity>>getFomatSectionsDB(String idFormat);
    Boolean checkIfFormatSectionsExists(String id);

    //Question
    void AddQuestionDB(QuestionEntity questionEntity);
    void UpdateQuestionDB(QuestionEntity questionEntity);
    Boolean checkIfQuestionsExists(String id);
    Single<List<QuestionEntity>>getQuestions();
    Single<List<QuestionEntity>>getQuestionsByIdSection(String id );
    Integer getCountQuestionsByFkSection(String id);

    //Option
    void AddOptionDB(OptionEntity optionEntity);
}
