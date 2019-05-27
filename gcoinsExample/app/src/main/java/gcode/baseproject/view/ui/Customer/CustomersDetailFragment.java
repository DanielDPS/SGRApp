package gcode.baseproject.view.ui.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import gcode.baseproject.R;
import gcode.baseproject.databinding.CustomersDetailFragmentBinding;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;

public class CustomersDetailFragment extends BaseFragment {


    private CustomersDetailFragmentBinding customersDetailFragmentBinding;
    private  static final String CUSTOMER_ARGS= "customer_args";
    private CustomerEntity customerEntity;
     public static final CustomersDetailFragment newInstance (CustomerEntity customerEntity){
        CustomersDetailFragment fragment = new CustomersDetailFragment();
        Bundle args= new Bundle();
        args.putSerializable(CUSTOMER_ARGS,customerEntity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            customerEntity= (CustomerEntity) getArguments().getSerializable(CUSTOMER_ARGS);


        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        customersDetailFragmentBinding = CustomersDetailFragmentBinding.inflate(inflater,container,false);
        return customersDetailFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customersDetailFragmentBinding.txtIdentifier.setText(customerEntity.getCIdentifier());
        customersDetailFragmentBinding.txtbusinessName.setText(customerEntity.getCBusinessName());
        customersDetailFragmentBinding.txtActivity.setText(String.valueOf(customerEntity.getCActivity()));
        customersDetailFragmentBinding.txtDirection.setText(customerEntity.getCDirection());
        customersDetailFragmentBinding.txtEntity.setText(customerEntity.getCEntity());
        customersDetailFragmentBinding.txtMail1.setText(customerEntity.getCMail1());
        customersDetailFragmentBinding.txtMail2.setText(customerEntity.getCMail2());
        customersDetailFragmentBinding.txtMail3.setText(customerEntity.getCMail3());
        customersDetailFragmentBinding.txtNumber1.setText(customerEntity.getCNumber1());
        customersDetailFragmentBinding.txtNumber2.setText(customerEntity.getCNumber2());
        customersDetailFragmentBinding.txtRfc.setText(customerEntity.getCRfc());
        customersDetailFragmentBinding.txtPermisoCRE.setText(customerEntity.getCCreationPermission());
    }

    @NonNull
    @Override
    public String getFragmentTag() {
        return CustomersDetailFragment.class.getName();
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(customersDetailFragmentBinding.toolbar)
                .withTitle("Detalle ["+customerEntity.getCIdentifier()+"]")
                .withBackButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                }).build();

        return builder;
    }
}

