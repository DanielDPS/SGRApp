package gcode.baseproject.domain.model.permissions;

import java.io.Serializable;
import androidx.annotation.NonNull;

public abstract class ModuleParameters implements Serializable {


    @NonNull
    private Module module;

    @NonNull
    private AccessLevel accessLevel;

    @NonNull
    private Permission permissions;


    public void setModule(Module module) {
        this.module = module;
    }

    @NonNull
    public Module getModule() {
        return module;
    }

    public void setAccessLevel(AccessLevel level) {
        accessLevel = level;
    }

    @NonNull
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setPermissions(Permission permission) {
        this.permissions = permission;
    }

    @NonNull
    public Permission getPermissions() {
        return permissions;
    }


}
