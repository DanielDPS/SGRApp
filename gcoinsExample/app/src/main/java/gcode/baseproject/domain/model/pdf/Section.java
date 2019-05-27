package gcode.baseproject.domain.model.pdf;

import java.util.List;

import androidx.annotation.Nullable;

public class Section {

    private String id;
    private String title;
    private boolean isMultiple;


    @Nullable
    private String multipleDescription;

    @Nullable
    private  List<SectionData> sectionDataList;

    @Nullable
    public List<SectionData> getSectionDataList() {
        return sectionDataList;
    }

    public void setSectionDataList(@Nullable List<SectionData> sectionDataList) {
        this.sectionDataList = sectionDataList;
    }

    private List<Question> questions;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultipleDescription(@Nullable String multipleDescription) {
        this.multipleDescription = multipleDescription;
    }

    @Nullable
    public String getMultipleDescription() {
        return multipleDescription;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


    public List<Question> getQuestions() {
        return questions;
    }
}
