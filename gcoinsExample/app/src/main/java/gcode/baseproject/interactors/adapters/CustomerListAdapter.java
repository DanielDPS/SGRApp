package gcode.baseproject.interactors.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.CustomerCardItemBinding;
import gcode.baseproject.interactors.db.entities.CustomerEntity;

public class CustomerListAdapter extends ArrayAdapter<CustomerEntity> {

    private List<CustomerEntity> customerEntities;
    private LayoutInflater layoutInflater;



    public CustomerListAdapter(@NonNull Context context, int resource, @NonNull List<CustomerEntity> items) {
        super(context, resource, items);
    }


}
