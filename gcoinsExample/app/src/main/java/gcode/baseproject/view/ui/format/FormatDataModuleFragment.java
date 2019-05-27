package gcode.baseproject.view.ui.format;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatDataModuleFragmentBinding;
import gcode.baseproject.interactors.adapters.CustomerItemAdapter;
import gcode.baseproject.interactors.date.CurrentDate;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.interactors.dialogs.CreateDialog;
import gcode.baseproject.interactors.md5.EncryptMD5;
import gcode.baseproject.interactors.session.SessionUser;
import gcode.baseproject.view.ui.Customer.CustomerFragment;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.viewmodel.Customer.CustomerViewModel;
import gcode.baseproject.view.viewmodel.format.FormatDataViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;

public class FormatDataModuleFragment extends BaseFragment {

    private  static String FORMAT_DATA_MODULE_ARGS ="format_data_module_args";
    private FormatDataModuleFragmentBinding formatDataModuleFragmentBinding;
    private FormatEntity format;
    private CustomerViewModel customerViewModel;
    private FormatDataViewModel formatDataViewModel;
    private  String idCustomer;
    private CustomerItemAdapter customerItemAdapter;
    private  CustomerEntity customerEntity;

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
            customerItemAdapter = new CustomerItemAdapter(getContext(),R.layout.customer_item_view,customerViewModel.listCustomersDB());
            formatDataModuleFragmentBinding.customers.setAdapter(customerItemAdapter);
            formatDataModuleFragmentBinding.txtDateIni.setText(CurrentDate.showDate());
            formatDataModuleFragmentBinding.customers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //customerEntity = customerItemAdapter.getItem(position);
                    customerEntity = customerViewModel.listCustomersDB().get(position);
                    //customerViewModel.ClearIdentifier(contextFragment);
                    //customerViewModel.getIdentifierObject(customerEntity.getCId(),formatDataModuleFragmentBinding);
                    customerViewModel.LoadIdentifier(customerEntity.getCId());
                    customerViewModel.getGetIdentifier()
                            .observe(contextFragment, new Observer<String>() {
                                @Override
                                public void onChanged(String identifier) {
                                    formatDataModuleFragmentBinding.txtIdentifier.setText(identifier);
                                    formatDataModuleFragmentBinding.titleIdentifier.setHint("Identificador");
                                }
                            });
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

    }
    @NonNull
    @Override
    public String getFragmentTag() {
        return FormatDataModuleFragment.class.getName();
    }
    private  List<Integer> getMenuItems(){
         List<Integer> menuItems = new ArrayList<>();
         menuItems.add(R.id.Add_form_module);
         return menuItems;
    }
    private  List<MenuItem.OnMenuItemClickListener> getMenuClicks (){
         List<MenuItem.OnMenuItemClickListener> listeners = new ArrayList<>();
         listeners.add(new MenuItem.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem item) {
                         if (customerEntity == null){

                             LayoutInflater inflater = LayoutInflater.from(getContext());
                             View view = inflater.inflate(R.layout.dialog_info, null);
                             MaterialButton btnInfo = view.findViewById(R.id.btnShowInfo);
                             ImageView img = view.findViewById(R.id.imgInfo);
                             TextView textInfo = view.findViewById(R.id.txtInfo);
                             CreateDialog myDialog = new CreateDialog();
                             final android.app.AlertDialog.Builder builder = myDialog.openDialog2(getContext(), view);
                             myDialog.setTitle("Salir");
                             builder.setIcon(R.drawable.ic_info_outline);
                             builder.setTitle("AVISO!");
                             //builder.setMessage(R.string.exit_dialog_message);
                             final android.app.AlertDialog alert = builder.create();
                             btnInfo.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     getNavigationManager().addFragment(CustomerFragment.getInstance());
                                     alert.dismiss();
                                 }
                             });
                             alert.show();



                         }else {
                             FormatDataEntity formatDataEntity = new FormatDataEntity();
                             formatDataEntity.setFdid(EncryptMD5.convertToMD5(CurrentDate.showDate()));
                             formatDataEntity.setEdited(CurrentDate.showDate());
                             formatDataEntity.setFkuser(SessionUser.getInstance().getAccount().getId());
                             formatDataEntity.setFkCustomer(customerEntity.getCId());
                             formatDataEntity.setFkformat(format.getCId());
                             formatDataEntity.setEstado01(0);
                             formatDataEntity.setEstado02(0);
                             formatDataEntity.setInitialDate(formatDataModuleFragmentBinding.txtDateIni.getText().toString());
                             formatDataEntity.setEndDate(CurrentDate.showDate());
                             formatDataEntity.setIdentifier(formatDataModuleFragmentBinding.txtIdentifier.getText().toString());
                             formatDataEntity.setCreated("f");
                             if (formatDataViewModel.CheckIFExists(formatDataEntity.getFkformat(),customerEntity.getCId(),formatDataEntity.getIdentifier())>0){
                                 Toast.makeText(getContext(),"Las inspeccion "+formatDataEntity.getIdentifier()+" ya existe",Toast.LENGTH_SHORT).show();
                             }else if (customerViewModel.listCustomersDB().size() <=0) {
                                 Toast.makeText(getContext(),"Favor de sincronizar clientes",Toast.LENGTH_SHORT).show();
                             }else {
                                 formatDataViewModel.AddFormatDataActions(formatDataEntity);
                                 getNavigationManager().addFragment(FormatSectionsFragment.newInstance(formatDataEntity));

                             }


                         }




                 return false;
             }
         });
         return listeners;
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(formatDataModuleFragmentBinding.toolbar)
                .withTitle("Nueva Inspección")
                .withMenu(R.menu.add_module_menu)
                .withMenuItems(getMenuItems(),getMenuClicks())
                .withBackButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                }).build();

        return builder;
    }
}
