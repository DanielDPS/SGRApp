package gcode.baseproject.domain.model.format;

public class FormatData {

    private  String Identifier;
    private  String Name;
    private  String Estado1;
    private  String Estado2;

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEstado1() {
        return Estado1;
    }

    public void setEstado1(String estado1) {
        Estado1 = estado1;
    }

    public String getEstado2() {
        return Estado2;
    }

    public void setEstado2(String estado2) {
        Estado2 = estado2;
    }
}
