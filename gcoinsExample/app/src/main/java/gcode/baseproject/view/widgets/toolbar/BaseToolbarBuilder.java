package gcode.baseproject.view.widgets.toolbar;

import android.view.MenuItem;
import android.view.View;
import java.util.List;

import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

public abstract class BaseToolbarBuilder {

    protected Toolbar toolbar;

    @StringRes
    protected int title;

    @Nullable
    protected CharSequence charTitle;

    protected int searchViewId;

    @MenuRes
    protected int menu;

    protected SearchView.OnQueryTextListener queryListener;

    protected SearchView.OnCloseListener closeListener;

    protected View.OnClickListener navigationListener;

    protected List<Integer> menuItems;

    protected List<MenuItem.OnMenuItemClickListener> menuClicks;


}
