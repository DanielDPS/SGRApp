package gcode.baseproject.view.ui.account;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.ActivityMainBinding;
import gcode.baseproject.databinding.NavHeaderBinding;
import gcode.baseproject.databinding.WelcomeFragmentBinding;
import gcode.baseproject.domain.model.account.Account;
import gcode.baseproject.domain.model.customer.Customer;
import gcode.baseproject.domain.model.customer.MyAdapter;
import gcode.baseproject.domain.model.permissions.AccessLevel;
import gcode.baseproject.domain.model.permissions.Module;
import gcode.baseproject.domain.model.permissions.ModuleParameters;
import gcode.baseproject.view.ui.Customer.CustomerFragment;
import gcode.baseproject.view.ui.format.FormatFragment;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.ui.general.ExitDialogFragment;
import gcode.baseproject.view.ui.general.FragmentFactory;
import gcode.baseproject.view.ui.welcome.WelcomeFragment;
import gcode.baseproject.view.utils.CircleTransformation;
import gcode.baseproject.view.utils.MenuBuilder;
import gcode.baseproject.view.utils.NavigationManager;
import gcode.baseproject.view.viewmodel.Customer.CustomerViewModel;
import gcode.baseproject.view.viewmodel.account.AccountViewModel;
import gcode.baseproject.view.viewmodel.account.HomeViewModel;
import gcode.baseproject.view.viewmodel.account.MenuViewModel;
import gcode.baseproject.view.viewmodel.account.PermissionsViewModel;
import gcode.baseproject.view.viewmodel.account.WelcomeViewModel;


public class MainActivity extends AppCompatActivity implements
                                                    NavigationView.OnNavigationItemSelectedListener,
                                                    NavigationManager.BackStackEventListener,
                                                    ExitDialogFragment.OnExitClickListener {

    // Data Binding
    private ActivityMainBinding mainBinding;
    private NavHeaderBinding navHeaderBinding;
    private WelcomeFragmentBinding welcomeFragmentBinding;

    // Navigation
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;


    // View Models
    private HomeViewModel homeViewModel;
    private AccountViewModel accountViewModel;
    private MenuViewModel menuViewModel;
    private PermissionsViewModel permissionsViewModel;
    private  CustomerViewModel customerViewModel;
    private  WelcomeViewModel welcomeViewModel;
    // Navigation Manager
    private NavigationManager navigationManager;

    // Menu Builder
    private MenuBuilder menuBuilder;

    // Fragment Factory
    private FragmentFactory fragmentFactory;


    private NavigationManager.BackStackEventListener listener;


    private ActionBarDrawerToggle buildDrawerToggle() {
        return new ActionBarDrawerToggle(this,
                        mDrawerLayout,
                        mToolbar,
                        R.string.drawer_open,
                        R.string.drawer_close);
    }


    private void setupUI() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void setupHomeViewModel() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    private void setupAccountViewModel() {
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
    }
    private  void setupCustomerViewModel(){
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
    }
    private void setupMenuViewModel() {
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
    }

    private void setupPermissionsViewModel() {
        permissionsViewModel = ViewModelProviders.of(this).get(PermissionsViewModel.class);
    }
    private  void setupWelcomeViewModel(){
        welcomeViewModel = ViewModelProviders.of(this).get(WelcomeViewModel.class);
    }

    private void setupNavigationManager() {
        listener = this;
        navigationManager = new NavigationManager(getSupportFragmentManager(), listener);
    }


    private void setupMenuBuilder() {
        menuBuilder = new MenuBuilder();
    }

    private void setupModuleFactory() {
        fragmentFactory = new FragmentFactory();
    }

    private void setupNavigationDrawer() {
        mToolbar = mainBinding.toolbar;
        mToolbar.setTitle(R.string.welcome_message);
        setSupportActionBar(mToolbar);
        mDrawerLayout = mainBinding.drawerLayout;
        mNavigationView = mainBinding.navView;
        navHeaderBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header, mNavigationView, false);
        mNavigationView.addHeaderView(navHeaderBinding.getRoot());
        mNavigationView.setNavigationItemSelectedListener(this);
    }


    private void setupDrawerToggle() {
        mDrawerToggle = buildDrawerToggle();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void setWelcomeFragment() {
        if (homeViewModel.getCurrentModule() == null) {
            WelcomeFragment welcomeFragment = WelcomeFragment.newInstance();
            navigationManager.addFragment(welcomeFragment);
            homeViewModel.setCurrentModule(Module.Welcome);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
        setupNavigationDrawer();
        setupDrawerToggle();
        setupHomeViewModel();
        setupAccountViewModel();
        setupCustomerViewModel();
        setupMenuViewModel();
        setupPermissionsViewModel();
        setupWelcomeViewModel();
        setupNavigationManager();
        setupMenuBuilder();
        setupModuleFactory();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onResume() {
        super.onResume();
        setWelcomeFragment();
        setAccountUI();
        setMenu();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // Navigation Callbacks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Observer<ModuleParameters> getModuleObserver() {
        return new Observer<ModuleParameters>() {
            @Override
            public void onChanged(ModuleParameters moduleParameters) {
                BaseFragment newFragment = fragmentFactory.createFragment(moduleParameters);
                navigationManager.addFragment(newFragment);
            }
        };
    }

    @Override
    public boolean onNavigationItemSelected(final @NonNull MenuItem item) {
        Module selectedModule = null;
        mDrawerLayout.closeDrawers();
        if (item.getItemId() == R.id.nav_log_out) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            ExitDialogFragment dialogFragment = ExitDialogFragment.newInstance();
            dialogFragment.show(fragmentManager, ExitDialogFragment.TAG);
        }
        switch (item.getItemId()) {
            case R.id.nav_clients:
                CustomerFragment customerFragment =CustomerFragment.getInstance();
                navigationManager.addFragment(customerFragment);
                break;
            case R.id.nav_formats:
                FormatFragment formatFragment = FormatFragment.getInstance();
                navigationManager.addFragment(formatFragment);
                break;
        }

        if (selectedModule != null) {
            permissionsViewModel.loadModulePermissions(selectedModule);
            permissionsViewModel
                    .getModulePermissions()
                    .observe(this, getModuleObserver());
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            if (navigationManager.shouldRemoveFromBackStack()) {
                navigationManager.removeFragmentFromBackStack();
            }
            else {
                homeViewModel.incrementBackPressedCounter();
                if (homeViewModel.shouldLogOut()) {
                    finish();
                    moveTaskToBack(true);
                }

            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        listener = null;
    }

    public void setAccountUI() {
        accountViewModel.load();
        accountViewModel
                .get()
                .observe(this,  getAccountObserver());
    }

    public void setMenu() {
        menuViewModel.loadMenu();
        menuViewModel
                .getMenu()
                .observe(this, getMenuObserver());
    }
    // Update Account Details Logic
    private Observer<Account> getAccountObserver() {
        return new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                updateUI(account);
             }
        };
    }

    private void updateUI(Account account) {

        welcomeViewModel.setAccountUser(account);
        navHeaderBinding.accountUsername.setText(account.getName());
        navHeaderBinding.accountRole.setText(account.getRole());

        Picasso.get()
                .load(account.getImage())
                .transform(new CircleTransformation())
                .into(navHeaderBinding.accountPhoto);


    }

    // Update Menu items logic
    private Observer<HashMap<Module, AccessLevel>> getMenuObserver() {
            return new Observer<HashMap<Module, AccessLevel>>() {
            @Override
            public void onChanged(HashMap<Module, AccessLevel> modulesAccessLevel) {
                menuBuilder.build(modulesAccessLevel, mNavigationView.getMenu());
            }
        };
    }

    // Listeners for back stack changes
    @Override
    public void onDrawerToggleDisabled() {
        ActionBar actionBar = getSupportActionBar();
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mNavigationView.setNavigationItemSelectedListener(null);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    public void onDrawerToggleEnabled() {
        ActionBar actionBar = getSupportActionBar();
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        actionBar.setDisplayHomeAsUpEnabled(false);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.setToolbarNavigationClickListener(null);
        mNavigationView.setNavigationItemSelectedListener(this);
        mToolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onExitConfirm() {
        homeViewModel.logOut();
    }

    @Override
    public void onExitCancel() {
        // Log
    }

    public NavigationManager getNavigationManager() {
        return navigationManager;
    }

}