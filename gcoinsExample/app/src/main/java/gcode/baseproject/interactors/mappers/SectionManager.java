package gcode.baseproject.interactors.mappers;

import java.util.List;

import gcode.baseproject.interactors.db.entities.FormatSectionEntity;

public class SectionManager {

    private static  List<Section> sectionsList;

    public static List<Section> getSectionsList() {
        return sectionsList;
    }
    public  static void AddSectionToList(Section section){
        sectionsList.add(section);
    }

    public static  void setSectionsList(List<Section> sections) {
        sectionsList = sections;
    }


}
