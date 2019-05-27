package gcode.baseproject.domain.model.pdf;

import java.util.List;

import androidx.annotation.NonNull;
import gcode.baseproject.interactors.db.entities.CustomerEntity;

public class Format {

    private  String idFormatData;
    private String description;
    private String identifier;
    private  String fkFormat;
    private  String edited;
    private  String fkUser;
    private  int state01;
    private  int state02;
    private  String initialDate;
    private  String endDate;

    public String getFkFormat() {
        return fkFormat;
    }

    public void setFkFormat(String fkFormat) {
        this.fkFormat = fkFormat;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getFkUser() {
        return fkUser;
    }

    public void setFkUser(String fkUser) {
        this.fkUser = fkUser;
    }

    public int getState01() {
        return state01;
    }

    public void setState01(int state01) {
        this.state01 = state01;
    }

    public int getState02() {
        return state02;
    }

    public void setState02(int state02) {
        this.state02 = state02;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private List<Section> sections;

    private CustomerEntity customer;

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


    public String getIdentifier() {
        return identifier;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setCustomer(@NonNull CustomerEntity customer) {
        this.customer = customer;
    }

    @NonNull
    public CustomerEntity getCustomer() {
        return customer;
    }
    public String getIdFormatData() {
        return idFormatData;
    }

    public void setIdFormatData(String idFormatData) {
        this.idFormatData = idFormatData;
    }

}
