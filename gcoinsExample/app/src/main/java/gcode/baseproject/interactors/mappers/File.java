package gcode.baseproject.interactors.mappers;

public class File {
    private  String IdFile;
    private  String FkFormatData;
    private  String Type;
    private  String PathFile;

    public String getIdFile() {
        return IdFile;
    }

    public void setIdFile(String idFile) {
        IdFile = idFile;
    }

    public String getFkFormatData() {
        return FkFormatData;
    }

    public void setFkFormatData(String fkFormatData) {
        FkFormatData = fkFormatData;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPathFile() {
        return PathFile;
    }

    public void setPathFile(String pathFile) {
        PathFile = pathFile;
    }
}
