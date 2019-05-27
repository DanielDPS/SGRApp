package gcode.baseproject.domain.model.pdf;

import java.util.List;

public class OptionManager {

    private static  List<Option > optionsList;

    public static List<Option> getOptionsList() {
        return optionsList;
    }

    public static void setOptionsList(List<Option> options) {
        optionsList = options;
    }
}
