package gcode.baseproject.interactors.mappers;

import java.util.ArrayList;
import java.util.List;

import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeader;

public class FileHeaderMapper {


    public  static  List<FileHeaderObject> ConverToFileHeaderObject(List<FileHeader> filesAPI){
        List<FileHeaderObject> objects = new ArrayList<>();
        for (FileHeader file : filesAPI){
            FileHeaderObject fileObject = new FileHeaderObject();
            fileObject.setType(file.getFileType());
            objects.add(fileObject);
        }
        return  objects;

    }



}
