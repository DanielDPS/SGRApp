package gcode.baseproject.interactors.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import gcode.baseproject.R;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.view.viewmodel.Customer.CustomerViewModel;
import gcode.baseproject.view.viewmodel.format.FormatDataViewModel;


public class ShowInfoSections extends DialogFragment {


     private FormatDataEntity formatDataObject;
     private  CustomerViewModel customerViewModel;
     private CustomerEntity customerEntity;
    private  TextView txtCustomer;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try{
            customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
            customerEntity = customerViewModel.getCustomerEntityById(formatDataObject.getFkCustomer());
            txtCustomer.setText(customerEntity.getCBusinessName());
        }catch (Exception e){
            e.printStackTrace();
            Log.e("TAG1","ERROR DIALOG "+e.getLocalizedMessage());
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.dialog_info_sections,container,false);

        Toolbar toolbar =view.findViewById(R.id.toolbar);
        toolbar.setTitle("Detalle formato");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        setHasOptionsMenu(true);

        TextView txtIdentifier = view.findViewById(R.id.txtIdentifierInfo);
        TextView txtInitialDate =view.findViewById(R.id.txtDateInitialInfo);
        TextView txtEndDate = view.findViewById(R.id.txtEndDateInfo);
        Chip txtState01= view.findViewById(R.id.txtState01Info);
        Chip txtState02 = view.findViewById(R.id.txtState02Info);
        txtCustomer=  view.findViewById(R.id.txtCustomerInfo);


        txtIdentifier.setText(formatDataObject.getIdentifier());
        txtInitialDate.setText(formatDataObject.getInitialDate());
        txtEndDate.setText(formatDataObject.getEndDate());
        txtState01.setText(formatDataObject.getTextState01());
        txtState02.setText(formatDataObject.getTextState02());



        return view;
    }
    public void setFormatObject (FormatDataEntity formatDataEntity){
        this.formatDataObject = formatDataEntity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}
