package gcode.baseproject.view.widgets.toolbar;

import android.view.MenuItem;
import android.view.View;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

public class CollapsingToolbarBuilder extends BaseToolbarBuilder {


    private CollapsingToolbarLayout layout;


    @Nullable
    private CharSequence subtitle;

    public CollapsingToolbarBuilder(Builder builder) {

        // Base class attributes
        toolbar = builder.toolbar;
        title = builder.title;
        charTitle = builder.charTitle;
        menu = builder.menu;
        searchViewId = builder.searchViewId;
        queryListener = builder.queryListener;
        closeListener = builder.closeListener;
        navigationListener = builder.navigationListener;
        menuItems = builder.menuItems;
        menuClicks = builder.menuClicks;

        // Subclassing attributes
        subtitle = builder.subtitle;

    }

    public static class Builder {

        private Toolbar toolbar;

        private CollapsingToolbarLayout layout;

        private int title;

        private CharSequence charTitle = null;

        private int menu;

        private int searchViewId;

        private SearchView.OnQueryTextListener queryListener;

        private SearchView.OnCloseListener closeListener;

        private View.OnClickListener navigationListener;

        private List<Integer> menuItems = new ArrayList<>();

        private List<MenuItem.OnMenuItemClickListener> menuClicks = new ArrayList<>();

        private CharSequence subtitle = null;


        public Builder(@NonNull Toolbar toolbar, @NonNull CollapsingToolbarLayout layout) {
            this.toolbar = toolbar;
            this.layout = layout;
        }

        public Builder withMenu(@MenuRes int menuId) {
            menu = menuId;
            return this;
        }


        public Builder withTitle(int title) {
            this.title = title;
            return this;
        }

        public Builder withTitle(CharSequence title) {
            charTitle = title;
            return this;
        }


        public Builder withSearchView(int searchViewId) {
            this.searchViewId = searchViewId;
            return this;
        }

        public Builder withFilterListener(SearchView.OnQueryTextListener listener) {
            queryListener = listener;
            return this;
        }

        public Builder withSearchCloseListener(SearchView.OnCloseListener listener) {
            closeListener = listener;
            return this;
        }

        public Builder withBackButton(View.OnClickListener listener) {
            this.navigationListener = listener;
            return this;
        }

        public Builder withMenuItems(List<Integer> menuItems, List<MenuItem.OnMenuItemClickListener> menuClicks) {
            this.menuItems.addAll(menuItems);
            this.menuClicks.addAll(menuClicks);
            return this;
        }

        public Builder withSubtitle(CharSequence subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public CollapsingToolbarBuilder build() {
            return new CollapsingToolbarBuilder(this);
        }

    }

    public int getSearchViewId() {
        return searchViewId;
    }

    public int getTitle() {
        return title;
    }

    @Nullable
    public CharSequence getCharTitle() {
        return charTitle;
    }

    @Nullable
    public CharSequence getSubtitle() {
        return subtitle;
    }

    public int getMenuId() {
        return menu;
    }


    public List<Integer> getMenuItems() {
        return menuItems;
    }

    public List<MenuItem.OnMenuItemClickListener> getMenuClicks() {
        return menuClicks;
    }


    public Toolbar getToolbar() {
        return toolbar;
    }

    public CollapsingToolbarLayout getCollapsingToolbarLayout() {
        return layout;
    }

    public SearchView.OnQueryTextListener getQueryListener() {
        return queryListener;
    }

    public SearchView.OnCloseListener getCloseListener() {
        return closeListener;
    }

    public View.OnClickListener getNavigationListener() {
        return navigationListener;
    }

}
