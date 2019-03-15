package gcode.baseproject.domain.repository.format;

import java.util.List;
import java.util.concurrent.ExecutionException;

import gcode.baseproject.domain.model.format.Format;
import gcode.baseproject.domain.model.format.FormatResponse;
import gcode.baseproject.domain.model.format.QuestionResponse;
import gcode.baseproject.domain.model.formatSection.FormatSection;
import gcode.baseproject.domain.model.question.Question;
import gcode.baseproject.interactors.api.NetworkManager;
import gcode.baseproject.interactors.api.format.FormatService;
import gcode.baseproject.interactors.api.format.QuestionService;
import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.FormatDao;
import gcode.baseproject.interactors.db.dao.FormatSectionDao;
import gcode.baseproject.interactors.db.dao.OptionDao;
import gcode.baseproject.interactors.db.dao.QuestionDao;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class FormatRepository implements  IFormatRepository{

    private FormatService formatService;
    private FormatDao formatDao;
    private FormatSectionDao formatSectionDao;
    private QuestionDao questionDao;
    private QuestionService questionService;
    private OptionDao optionDao;
    public FormatRepository (){
        formatService = NetworkManager.getInstance().create(FormatService.class);
        questionService = NetworkManager.getInstance().create(QuestionService.class);
        formatDao = ApplicationDatabase.getDatabase().getFormatDao();
        formatSectionDao = ApplicationDatabase.getDatabase().getFormatSectionDao();
        questionDao = ApplicationDatabase.getDatabase().getQuestionDao();
        optionDao = ApplicationDatabase.getDatabase().getOptionDao();

    }

    @Override
    public Single<List<Format>> getAllFormatsAndSections(String token, String lastUpdate) {
        return formatService.getFormatsAndSections(token,lastUpdate)
                .map(new Function<FormatResponse, List<Format>>() {
                    @Override
                    public List<Format> apply(FormatResponse formatResponse) throws Exception {
                        return formatResponse.getFormats();
                    }
                });
    }

    @Override
    public Single<List<Question>> getAllQuestionsAndOptions(String token, String lastUpdate, String idSection) {
        return questionService.getFormatsAndSections(token,lastUpdate,idSection)
                .map(new Function<QuestionResponse, List<Question>>() {
                    @Override
                    public List<Question> apply(QuestionResponse questionResponse) throws Exception {
                        return questionResponse.getQuestions();
                    }
                });
    }


    @Override
    public void AddFormatDB(FormatEntity formatEntity) {
        formatDao.AddFormat(formatEntity);
    }

    @Override
    public void UpdateFormatDB(FormatEntity formatEntity) {
        formatDao.UpdateFormat(formatEntity);
    }

    @Override
    public void DeleteFormatDB(FormatEntity formatEntity) {
        formatDao.RemoveFormat(formatEntity);
    }

    @Override
    public List<FormatEntity> getFormatsDB() {
        GetFormatsAsyncTask task  = new GetFormatsAsyncTask(formatDao);
        try {
            task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getFormatEntities();
    }


    @Override
    public Boolean checkIfFormatsExists(String id) {
        return formatDao.getCountFormats(id) >0;
    }

    @Override
    public Boolean checkIfFormatsUpdate(String id, String lastUpdate) {
        return formatDao.checkIfFormatsShouldUpdate(id,lastUpdate) > 0;
    }

    @Override
    public String getFormatLastUpdate() {
        String lastUpdate = null;
        try{
        lastUpdate= formatDao.getMaxLastUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return lastUpdate == null ? "" : lastUpdate;
    }

    /** FORMAT SECTION **/

    @Override
    public void AddFormatSectionDB(FormatSectionEntity formatSectionEntity) {
        formatSectionDao.AddFormatSection(formatSectionEntity);
    }

    @Override
    public void UpdateFormatSectionDB(FormatSectionEntity formatSectionEntity) {
        formatSectionDao.UpdateFormatSection(formatSectionEntity);
    }

    @Override
    public Single<List<FormatSectionEntity>> getFomatSectionsDB(String idFormat) {
        return formatSectionDao.getFormatSections(idFormat);
    }

    @Override
    public Boolean checkIfFormatSectionsExists(String id) {
        return formatSectionDao.getCountFormatSections(id) > 0;
    }

    @Override
    public void AddQuestionDB(QuestionEntity questionEntity) {
        questionDao.AddQuestion(questionEntity);

    }



    /** FORMAT SECTION **/


    /** Questions**/
    @Override
    public void UpdateQuestionDB(QuestionEntity questionEntity) {
        questionDao.UpdateQuestion(questionEntity);
    }

    @Override
    public Boolean checkIfQuestionsExists(String id) {
        return questionDao.getCountQuestions(id) >0;
    }

    @Override
    public Single<List<QuestionEntity>> getQuestions() {
        return questionDao.getAllQuestions();
    }

    @Override
    public Single<List<QuestionEntity>> getQuestionsByIdSection(String id) {
        return questionDao.getquestionsByFkSection(id);
    }

    @Override
    public Integer getCountQuestionsByFkSection(String id) {

        GetQuestionsByFkSectionAsyncTask task = new GetQuestionsByFkSectionAsyncTask(questionDao);
        try {
            task.execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getTotal();
    }



    /** Questions **/

    /** OPTIONS **/
    @Override
    public void AddOptionDB(OptionEntity optionEntity) {
        optionDao.AddOption(optionEntity);
    }

    /** OPTIONS **/


}
