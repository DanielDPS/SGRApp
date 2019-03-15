package gcode.baseproject.domain.model.permissions;

import java.io.Serializable;

public class Permission implements Serializable {



    private boolean addable;
    private boolean editable;
    private boolean readable;
    private boolean removeable;
    private boolean hasSuperAccess;

    public void setAddable(boolean addable) {
        this.addable = addable;
    }

    public boolean isAddable() {
        return addable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public boolean isReadable() {
        return readable;
    }

    public void setRemoveable(boolean removeable) {
        this.removeable = removeable;
    }

    public boolean isRemoveable() {
        return removeable;
    }

    public void setSuperAccess(boolean superAccess) {
        this.hasSuperAccess = superAccess;
    }

    public boolean hasSuperAccess() {
        return hasSuperAccess;
    }
}
