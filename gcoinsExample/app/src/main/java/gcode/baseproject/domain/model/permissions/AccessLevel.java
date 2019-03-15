package gcode.baseproject.domain.model.permissions;

public enum AccessLevel {

    SuperAccess(1),
    Access(2),
    SettingsError(3);

    private final int value;

    AccessLevel(final int newValue){
        value = newValue;
    }

    public int getValue() {
        return value;
    }

    public static AccessLevel parse(int value) {
        for (AccessLevel a : AccessLevel.values()) {
            if (a.getValue() == value) {
                return a;
            }
        }
        return null;
    }
}
