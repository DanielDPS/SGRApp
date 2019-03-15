package gcode.baseproject.domain.model.permissions;

import androidx.annotation.Nullable;

public enum Module {

    Welcome("Welcome"),

    VisitGuide("guia_visita"),

    Orders("pedidos"),

    Payments("pagos"),

    Cut("cortes"),

    Deposits("depositos");

    private String value;

    Module(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }

    @Nullable
    public static Module parse(String value) {
        for (Module m : Module.values()) {
            if (m.getValue().equals(value)) {
                return m;
            }
        }
        return null;
    }

}
