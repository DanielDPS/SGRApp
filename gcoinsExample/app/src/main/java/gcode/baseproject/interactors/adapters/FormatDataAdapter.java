package gcode.baseproject.interactors.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatDataCardItemBinding;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.view.ui.format.FormatDataFragment;
import gcode.baseproject.view.viewmodel.Customer.CustomerViewModel;

public class FormatDataAdapter  extends    RecyclerView.Adapter<FormatDataAdapter.FormatDataViewHolder> {


    private LayoutInflater layoutInflater;
    private List<FormatDataEntity> formatDataList;
    private CustomerViewModel customerViewModel;
    FormatDataFragment formatDataFragment ;
    public  FormatDataAdapter (List<FormatDataEntity> dataList,FormatDataFragment fragment)
    {
        this.formatDataList= dataList;
        this.formatDataFragment = fragment;
    }
    @NonNull
    @Override
    public FormatDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        FormatDataCardItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.format_data_card_item,parent,false);
        return new FormatDataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FormatDataViewHolder holder, int position) {
        FormatDataEntity format = formatDataList.get(position);
        customerViewModel= ViewModelProviders.of(this.formatDataFragment).get(CustomerViewModel.class);
        CustomerEntity customerEntity = customerViewModel.getCustomerEntityById(format.getFkCustomer());
        holder.binding.formatIdentifier.setText(format.getIdentifier());
        holder.binding.formatDescription.setText(format.getEdited());
        holder.binding.formatDataPendiente.setText(format.getTextState01());
        holder.binding.formatDataFinalizado.setText(format.getTextState02());
        holder.binding.formatDescription.setText(customerEntity.getCBusinessName()+"("+customerEntity.getCIdentifier()+")");

    }

    @Override
    public int getItemCount() {

        return formatDataList.size();
    }


    public static class FormatDataViewHolder extends RecyclerView.ViewHolder{


        private FormatDataCardItemBinding binding;
        public FormatDataViewHolder(FormatDataCardItemBinding binding){
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}
