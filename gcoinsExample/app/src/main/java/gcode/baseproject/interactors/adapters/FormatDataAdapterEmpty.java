package gcode.baseproject.interactors.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.itextpdf.text.Rectangle;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
 import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatDataCardEmptyBinding;

public class FormatDataAdapterEmpty  extends RecyclerView.Adapter<FormatDataAdapterEmpty.FormatDataEmptyViewHolder> {


    private LayoutInflater layoutInflater;
     public  FormatDataAdapterEmpty ()
    {
    }

    @NonNull
    @Override
    public FormatDataEmptyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater ==null){
            layoutInflater=  LayoutInflater.from(parent.getContext());
        }
        FormatDataCardEmptyBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.format_data_card_empty,parent,false);
        return new FormatDataEmptyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FormatDataEmptyViewHolder holder, int position) {
         holder.binding.formatIdentifier.setText("No existen datos");

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class FormatDataEmptyViewHolder extends RecyclerView.ViewHolder{


        private FormatDataCardEmptyBinding binding;
        public FormatDataEmptyViewHolder(FormatDataCardEmptyBinding binding){
            super(binding.getRoot());
            this.binding= binding;
        }
    }



}
