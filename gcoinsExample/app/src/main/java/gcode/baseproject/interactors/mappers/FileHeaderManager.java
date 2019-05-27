package gcode.baseproject.interactors.mappers;

import java.util.List;

import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeader;

public class FileHeaderManager {

    private static List<FileHeader> listFiles ;

    public static List<FileHeader> getListFiles() {
        return listFiles;
    }

    public static void setListFiles(List<FileHeader> list) {
        listFiles = list;
    }
}
