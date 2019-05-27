package gcode.baseproject.interactors.mappers;

import java.util.List;

public class FileManager {

    private static  List<File> filesList ;

    public static List<File> getFilesList() {
        return filesList;
    }

    public static void setFilesList(List<File> files) {
        filesList = files;
    }
}
