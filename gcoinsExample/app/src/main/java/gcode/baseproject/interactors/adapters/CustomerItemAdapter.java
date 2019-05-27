package gcode.baseproject.interactors.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import gcode.baseproject.R;
import gcode.baseproject.interactors.db.entities.CustomerEntity;

public class CustomerItemAdapter extends ArrayAdapter<CustomerEntity> {


    private LayoutInflater inflater;
    private  int mIntResource;
    private  List<CustomerEntity> listCustomers;
    private  Context mContext;

    public CustomerItemAdapter(@NonNull Context context, int resource, @NonNull List<CustomerEntity> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mIntResource = resource;
        this.listCustomers = objects;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getViewCustomer(position,convertView,parent);
    }
    @Nullable
    @Override
    public CustomerEntity getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getViewCustomer(position,convertView,parent);
    }
    private   View getViewCustomer(int position,View convertView,ViewGroup parent){
        View view = inflater.inflate(this.mIntResource,parent,false);
        TextView txtCustomer = view.findViewById(R.id.txtCustomerName);
        CustomerEntity customer = listCustomers.get(position);
        txtCustomer.setText("["+customer.getCIdentifier()+ "]"+"-"+ customer.getCBusinessName());


        return  view;

    }

}
