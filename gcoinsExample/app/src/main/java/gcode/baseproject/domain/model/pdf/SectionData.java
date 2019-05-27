package gcode.baseproject.domain.model.pdf;

public class SectionData {

    private  String IdSectionData;

    public String getIdSectionData() {
        return IdSectionData;
    }

    public void setIdSectionData(String idSectionData) {
        IdSectionData = idSectionData;
    }

    private String Reference;
    private  int Folio;

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public int getFolio() {
        return Folio;
    }

    public void setFolio(int folio) {
        Folio = folio;
    }
}
