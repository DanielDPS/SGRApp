package gcode.baseproject.view.widgets.toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import gcode.baseproject.R;

public class ToolbarManager {

    private BaseToolbarBuilder builder;

    public ToolbarManager(BaseToolbarBuilder builder) {
        this.builder = builder;
    }

    public void prepareToolbar() {

        if (builder instanceof ToolbarBuilder) {
            Log.d("TAG1","Se trata de un toolbar normal : v");
            prepareToolbarBuilder();
        }
        else if (builder instanceof CollapsingToolbarBuilder) {
            prepareCollapsingToolbar();
        }
        else if (builder instanceof NullToolbarBuilder) {

        }
    }

    private void prepareToolbarBuilder() {
        ToolbarBuilder builder = (ToolbarBuilder) this.builder;
        Toolbar toolbar = builder.getToolbar();
        if (builder.getTitle() != 0) {
            toolbar.setTitle(builder.getTitle());
        }

        if (builder.getCharTitle() != null) {
            toolbar.setTitle(builder.getCharTitle());
        }

        if (builder.getMenuId() != 0) {
            toolbar.inflateMenu(builder.getMenuId());
        }

        if (builder.getSearchViewId() != 0) {
            MenuItem item = toolbar.getMenu().findItem(builder.getSearchViewId());
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(builder.getQueryListener());
            searchView.setOnCloseListener(builder.getCloseListener());
        }

        if (builder.getNavigationListener() != null) {
            toolbar.setNavigationIcon(R.drawable.ic_action_back);
            toolbar.setNavigationOnClickListener(builder.getNavigationListener());
        }

        if (builder.getMenuItems() != null) {
            int size = builder.getMenuItems().size();

            //donde lo agregaste ? en tooobar manager, ya con esto no es necesario que lo agreges en otra parte
            // te mande la clase pero no te la mande completa, pero ya we

            // Pariente le falltaba el metodo para inflar el toolbar we, :v , los menus , jajaja
            // que raro segun yo te lo habia mandado ya listo
            // :v fale ferga
            // correlo ya debeira de funcionar no we solo me dijiste de los metodos arre vamos ae pariente vere
             List<Integer> menuItems = builder.getMenuItems();
            List<MenuItem.OnMenuItemClickListener> listeners = builder.getMenuClicks();
            Menu menu = toolbar.getMenu();

            for (int i = 0; i < size; i++) {
                int id = menuItems.get(i);
                MenuItem item = menu.findItem(id);
                item.setOnMenuItemClickListener(listeners.get(i));
            }
        }
    }

    private void prepareCollapsingToolbar() {

        CollapsingToolbarBuilder builder = (CollapsingToolbarBuilder) this.builder;
        Toolbar toolbar = builder.getToolbar();
        CollapsingToolbarLayout toolbarLayout = builder.getCollapsingToolbarLayout();

        if (builder.getTitle() != 0) {
            toolbar.setTitle(builder.getTitle());
        }

        if (builder.getCharTitle() != null) {
            toolbar.setTitle(builder.getCharTitle());
        }

        if (builder.getSubtitle() != null) {
            toolbar.setSubtitle(builder.getSubtitle());
        }

        if (builder.getMenuId() != 0) {
            toolbar.inflateMenu(builder.getMenuId());
        }

        if (builder.getSearchViewId() != 0) {
            MenuItem item = toolbar.getMenu().findItem(builder.getSearchViewId());
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(builder.getQueryListener());
            searchView.setOnCloseListener(builder.getCloseListener());
        }

        if (builder.getNavigationListener() != null) {
            toolbar.setNavigationIcon(R.drawable.ic_action_back);
            toolbar.setNavigationOnClickListener(builder.getNavigationListener());
        }

    }

}
