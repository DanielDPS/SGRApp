package gcode.baseproject.view.ui.format;

import android.app.DatePickerDialog;
import android.media.midi.MidiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Currency;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.adapters.ToolbarBindingAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatDataFragmentBinding;
import gcode.baseproject.databinding.FormatDataModuleFragmentBinding;
import gcode.baseproject.interactors.date.CurrentDate;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.interactors.session.SessionUser;
import gcode.baseproject.view.ui.Customer.CustomerFragment;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.viewmodel.Customer.CustomerViewModel;
import gcode.baseproject.view.viewmodel.format.FormatDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

public class FormatDataModuleFragment extends BaseFragment {

    private  static String FORMAT_DATA_MODULE_ARGS ="format_data_module_args";
    private FormatDataModuleFragmentBinding formatDataModuleFragmentBinding;
    private FormatEntity format;
    private CustomerViewModel customerViewModel;
    private FormatDataViewModel formatDataViewModel;
    private  String idCustomer;
     public static final FormatDataModuleFragment newInstance(FormatEntity format){
        FormatDataModuleFragment formatDataModuleFragment = new FormatDataModuleFragment();
        Bundle args= new Bundle();
        args.putSerializable(FORMAT_DATA_MODULE_ARGS,format);
        formatDataModuleFragment.setArguments(args);
        return formatDataModuleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null ){
            format = (FormatEntity) getArguments().getSerializable(FORMAT_DATA_MODULE_ARGS);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        formatDataModuleFragmentBinding = FormatDataModuleFragmentBinding.inflate(inflater,container,false);
        return formatDataModuleFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        formatDataViewModel = ViewModelProviders.of(this).get(FormatDataViewModel.class);
    }
    @Override
    public void onResume() {
        super.onResume();
        final FormatDataModuleFragment contextFragment = this;

        customerViewModel.LoadCustomerNames();
        customerViewModel.getmCustomerName()
                .observe(this, new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> names) {
                        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,names);
                        formatDataModuleFragmentBinding.customers.setAdapter(namesAdapter);
                        formatDataModuleFragmentBinding.txtDateIni.setText(CurrentDate.showDate());
                        formatDataModuleFragmentBinding.customers.setOnItemSelectedListener(new customerList(contextFragment));

                    }
                });

        formatDataModuleFragmentBinding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TestObserver testObserver = new TestObserver();
                Completable action = Completable.fromRunnable(new Runnable() {
                    @Override
                    public void run() {

                            FormatDataEntity formatDataEntity = new FormatDataEntity();
                            formatDataEntity.setFdid(String.valueOf(System.currentTimeMillis()));
                            formatDataEntity.setEdited(CurrentDate.showDate());
                            formatDataEntity.setFkuser(SessionUser.getInstance().getAccount().getId());
                            formatDataEntity.setFkCustomer(idCustomer);
                            formatDataEntity.setFkformat(format.getCId());
                            formatDataEntity.setEstado01(0);
                            formatDataEntity.setEstado02(1);
                            formatDataEntity.setIdentifier(formatDataModuleFragmentBinding.txtIdentifier.getText().toString());

                                formatDataViewModel.AddFormatDataActions(formatDataEntity);
                                getNavigationManager().addFragment(FormatSectionsFragment.newInstance(formatDataEntity));

                    }
                });
                action.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(testObserver);
                testObserver.assertNoErrors();
            }
        });


    }

    public class customerList implements  AdapterView.OnItemSelectedListener{

        private  FormatDataModuleFragment fragment;
        public customerList(FormatDataModuleFragment moduleFragment){
            this.fragment= moduleFragment;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String customername =parent.getItemAtPosition(position).toString();
            idCustomer =customerViewModel.getCustomerId(customername);
            customerViewModel.ClearIdentifier(this.fragment);
             customerViewModel.LoadIdentifier(idCustomer);
            customerViewModel.getGetIdentifier()
                    .observe(this.fragment, new Observer<String>() {
                        @Override
                        public void onChanged(String identifier) {
                            formatDataModuleFragmentBinding.txtIdentifier.setText(identifier);
                        }
                    });


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @NonNull
    @Override
    public String getFragmentTag() {
        return FormatDataModuleFragment.class.getName();
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(formatDataModuleFragmentBinding.toolbar)
                .withTitle("Modulo formato")
                .withMenu(R.menu.drawer_menu)
                .withBackButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                }).build();

        return builder;
    }
}
