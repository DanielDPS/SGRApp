package gcode.baseproject.interactors.mappers;

import java.util.ArrayList;
import java.util.List;

import gcode.baseproject.interactors.db.entities.FormatSectionEntity;

public class SectionMapper {

    private  List<Section> list ;

    public List<Section> getList() {
        return list;
    }

    public void setList(List<Section> list) {
        this.list = list;
    }

    public static  List<Section>ConvertToSectionDBToSection (List<FormatSectionEntity> sectionsDB){
        List<Section > sections = new ArrayList<>();
        for (FormatSectionEntity section  : sectionsDB){
            Section section1 = new Section();
            section1.setIdSection(section.getId());
            section1.setName(section.getDescription());
            sections.add(section1);
        }
        return  sections;
    }

}
