package gcode.baseproject.interactors.mappers;

import java.util.ArrayList;
import java.util.List;

import gcode.baseproject.interactors.db.entities.data.FileDataEntity;

public class FileMapper {


    public static List<File> ConverListFileDbToListFile(List<FileDataEntity> files){
        List<File> filestemp = new ArrayList<>();
        for (FileDataEntity fileDb : files){
            File file=new File();
            file.setIdFile(fileDb.getId());
            file.setFkFormatData(fileDb.getFkFormatData());
            file.setType(fileDb.getType());
            file.setPathFile(fileDb.getFile());
            filestemp.add(file);
        }

        return  filestemp;
    }


}
