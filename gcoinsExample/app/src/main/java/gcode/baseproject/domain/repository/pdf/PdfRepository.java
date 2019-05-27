package gcode.baseproject.domain.repository.pdf;

import android.support.v4.app.INotificationSideChannel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import gcode.baseproject.domain.model.pdf.Answer;
import gcode.baseproject.domain.model.pdf.Format;
import gcode.baseproject.domain.model.pdf.Option;
import gcode.baseproject.domain.model.pdf.OptionManager;
import gcode.baseproject.domain.model.pdf.Question;
import gcode.baseproject.domain.model.pdf.Section;
import gcode.baseproject.domain.model.pdf.SectionData;
import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.CustomerDao;
import gcode.baseproject.interactors.db.dao.FormatDao;
import gcode.baseproject.interactors.db.dao.FormatSectionDao;
import gcode.baseproject.interactors.db.dao.OptionDao;
import gcode.baseproject.interactors.db.dao.QuestionDao;
import gcode.baseproject.interactors.db.dao.data.EvidenceDataDao;
import gcode.baseproject.interactors.db.dao.data.FormatDataDao;
import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;
import gcode.baseproject.interactors.db.dao.data.SectionDataDao;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;

public class PdfRepository {



    // Format Data
    private FormatDao formatDao;
    private OptionDao optionDao;
    private QuestionDao questionDao;
    private FormatSectionDao sectionDao;
    private CustomerDao customerDao;
    // Answers Data

    private FormatDataDao formatDataDao;
    private QuestionDataDao questionDataDao;
    private SectionDataDao sectionDataDao;
    private EvidenceDataDao evidenceDataDao;

    public PdfRepository() {

        formatDao = ApplicationDatabase.getDatabase().getFormatDao();
        questionDao = ApplicationDatabase.getDatabase().getQuestionDao();
        optionDao = ApplicationDatabase.getDatabase().getOptionDao();
        sectionDao = ApplicationDatabase.getDatabase().getFormatSectionDao();
        customerDao = ApplicationDatabase.getDatabase().getCustomerDao();

        // Answer
        formatDataDao = ApplicationDatabase.getDatabase().getFormatDataDao();
        questionDataDao = ApplicationDatabase.getDatabase().getQuestionDataDao();
        sectionDataDao = ApplicationDatabase.getDatabase().getSectionDataDao();
        evidenceDataDao = ApplicationDatabase.getDatabase().getEvidenceDataDao();
    }

    // Sub Functions to do Stuff


    private boolean isMultireference(FormatSectionEntity entity) {
        boolean isMultiReference = false;
        if (entity.getIsmultipleref() != null  ) {
            if (!entity.getIsmultipleref().equals("") && entity.getMultipledescription()!=null) {
                isMultiReference = true;
            }
        }
        return isMultiReference;
    }
    private  List<SectionData> getSectionData(String sectionId,String fkFormatData) {
        List<SectionData> sectionDataList = new ArrayList<>();
        List<SectionDataEntity> sectionsDB=  sectionDataDao.getSectionsListByFKSectionAndFkFD(sectionId,fkFormatData);
        for (SectionDataEntity section : sectionsDB){
            SectionData sectionData = new SectionData();
            sectionData.setIdSectionData(section.getId());
            sectionData.setFolio(section.getFolio());
            sectionData.setReference(section.getReference());
            sectionDataList.add(sectionData);


        }
        return  sectionDataList;

    }

    private List<Section> convertDbSectionsToSections(List<FormatSectionEntity> databaseSections,String idFormatData) {
        List<Section> sections =  new ArrayList<>();
        for (FormatSectionEntity entity : databaseSections) {

            Section section = new Section();
            section.setId(entity.getId());
            section.setTitle(entity.getDescription());
            if (isMultireference(entity)) {
                section.setMultiple(true);
                section.setMultipleDescription(entity.getMultipledescription());
                section.setSectionDataList(getSectionData(section.getId() ,idFormatData));
            }
            else {
                section.setMultiple(false);
                section.setMultipleDescription(null);
            }
            sections.add(section);
        }
        return sections;
    }


    private List<Question> convertDbQuestionsToQuestions(List<QuestionEntity> dbQuestions) {
        List<Question> questions = new ArrayList<>();
        for (QuestionEntity entity : dbQuestions) {
            Question question = new Question();
            question.setId(entity.getId());
            question.setTitle(entity.getDescription());
            question.setOrder(entity.getOrder());
            question.setFound(entity.getFundament());
            question.setHallazgo(entity.getHallazgo());
            question.setCritical(entity.getCritical().equals("t"));
            question.setType(entity.getFieldtype());
            questions.add(question);
        }
        return questions;
    }



    @Nullable
    private List<String> getEvidencesByQuestionId(String questionId) {
        Integer evidenceCount = evidenceDataDao.getCountEvidence(questionId);

        if (evidenceCount != null) {
            if (evidenceCount > 0) {
                List<EvidenceDataEntity> evidences = evidenceDataDao.getEvidenceListByFkQuestionData(questionId);
                List<String> images = new ArrayList<>();
                for (EvidenceDataEntity entity : evidences) {
                    images.add(entity.getImageUrl());
                }
                return images;
            }else {
                Log.d("TAG1","evidence empty");
            }
        }
        return null;
    }

    private List<Option> convertDbOptionsToOption(List<OptionEntity> dbOptions) {
        List<Option> options = new ArrayList<>();
        for (OptionEntity entity : dbOptions) {

            Option option = new Option();
            option.setId(entity.getId());
            option.setTitle(entity.getDescription());



            options.add(option);
        }
        return options;
    }
    private Answer getQuestionAnswer(String questionId,String ifFormatData) {

        QuestionDataEntity data = questionDataDao.getSectionsByFKS(questionId,ifFormatData);

        Answer answer = new Answer();
        answer.setText(data.getTextQuestion());
        answer.setObservation(data.getObservation());
        answer.setImagesUrls(getEvidencesByQuestionId(data.getId()));
        return answer;
    }

    private void getDataForQuestions(List<Question> questions,String idFormatData) {
        for (Question question : questions) {

            // Get The options for each question
            List<OptionEntity> dbOptions = optionDao.getOptionsByFkQuestion(question.getId());
            List<Option> options = convertDbOptionsToOption(dbOptions);
            question.setOptions(options);

            // Get the Question Answer

            Answer answer = getQuestionAnswer(question.getId(),idFormatData);
            question.setAnswer(answer);
        }
    }

    private  Answer getQuestionsAnswerMultiple (String questionId,String idFormatData,String fkSectionData){
        QuestionDataEntity data = questionDataDao.getAnswerObjectMultiple(questionId,idFormatData,fkSectionData);
        Answer answerMultiple  = new Answer();
        answerMultiple.setText(data.getTextQuestion());
        answerMultiple.setObservation(data.getObservation());
        answerMultiple.setImagesUrls(getEvidencesByQuestionId(data.getId()));
        return  answerMultiple;
    }

    private void getDataForQuestionsMultiple(List<Question> questions,String idFormatData,String idSectionData) {
        for (Question question : questions) {

            // Get The options for each question
            List<OptionEntity> dbOptions = optionDao.getOptionsByFkQuestion(question.getId());
            List<Option> options = convertDbOptionsToOption(dbOptions);
            question.setOptions(options);

            // Get the Question Answer

            Answer answer = getQuestionsAnswerMultiple(question.getId(),idFormatData,idSectionData);
            question.setAnswerMultiple(answer);
        }
    }

    @Nullable
    public Format getFormatById(String id) {

        // Get The current inspection
        FormatDataEntity inspection = formatDataDao.getObjectFormatDataById(id);

        // Get the format of the inspection
        FormatEntity formatEntity = formatDao.getObjectFormatById(inspection.getFkformat());

        // Get The Sections of the format
        List<FormatSectionEntity> dbSections =
                sectionDao.getListSectionsByFkFormat(formatEntity.getCId());

        // Map Sections
        List<Section> sections = convertDbSectionsToSections(dbSections,inspection.getFdid());

        // Customer
        CustomerEntity customerEntity = customerDao.getCustomerById(inspection.getFkCustomer());


        // Get Question and Answers for Sections

        for (Section section : sections) {

            // Get Sections Question From database
            List<QuestionEntity> dbQuestions = questionDao.getQuestionsByIdSection(section.getId());

            // Map Questions
            List<Question> questions = convertDbQuestionsToQuestions(dbQuestions);

            // Get Options and data for the section questions
            getDataForQuestions(questions,inspection.getFdid());


            // Set the questions
            section.setQuestions(questions);


        }

        //printData(sections);
        // Format
        Format newFormat = new Format();
        newFormat.setIdFormatData(inspection.getFdid());
        newFormat.setIdentifier(inspection.getIdentifier());
        newFormat.setFkFormat(inspection.getFkformat());
        newFormat.setEdited(inspection.getEdited());
        newFormat.setFkUser(inspection.getFkuser());
        newFormat.setState01(inspection.getEstado01());
        newFormat.setState02(inspection.getColorState02());
        newFormat.setInitialDate(inspection.getInitialDate());
        newFormat.setEndDate(inspection.getEndDate());
        newFormat.setDescription(formatEntity.getCDescription());
        newFormat.setSections(sections);
        newFormat.setCustomer(customerEntity);
        return newFormat;
    }


    private void printData(List<Section> sections) {
        try {
            for (Section section : sections) {
                String message = String.format("%s tiene %d", section.getTitle(), section.getQuestions().size());
                Log.d("TAG1", message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
