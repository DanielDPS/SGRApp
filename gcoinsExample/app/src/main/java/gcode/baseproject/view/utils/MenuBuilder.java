package gcode.baseproject.view.utils;

import android.view.Menu;
import android.view.MenuItem;
import java.util.HashMap;
import java.util.Map;

import gcode.baseproject.R;
import gcode.baseproject.domain.model.permissions.AccessLevel;
import gcode.baseproject.domain.model.permissions.Module;

/**
 *
 * @author Juan Pablo Canseco Rios
 * Helper class to create the application menu according to the given rol
 * @class MenuBuilder
 *
 * ***/

public class MenuBuilder {

    public void build(HashMap<Module, AccessLevel> map, Menu menu) {
        for (Map.Entry<Module, AccessLevel> entry : map.entrySet()) {
            disableMenuItem(entry.getKey(), entry.getValue(), menu);
        }
    }

    private MenuItem getMenuItem(Module moduleParameters, Menu menu) {
        MenuItem menuItem = null;
        switch (moduleParameters) {

        }
        return menuItem;
    }

    private void disableMenuItem(Module moduleParameters, AccessLevel accessLevel, Menu menu) {
        MenuItem menuItem = getMenuItem(moduleParameters, menu);
        if (accessLevel == AccessLevel.SettingsError) {
            menuItem.setEnabled(false);
            menuItem.setVisible(false);
        }
    }
}
