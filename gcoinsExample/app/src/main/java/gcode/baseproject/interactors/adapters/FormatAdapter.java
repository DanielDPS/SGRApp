package gcode.baseproject.interactors.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatCardItemBinding;
import gcode.baseproject.domain.model.format.Format;
import gcode.baseproject.interactors.db.entities.FormatEntity;

public class FormatAdapter extends  RecyclerView.Adapter<FormatAdapter.FormatViewHolder>{

    private List<FormatEntity> formatsList;
    private  FormatPresenter presenter;
    private LayoutInflater layoutInflater;
    public  FormatAdapter(List<FormatEntity > formats,FormatPresenter presenter)
    {
        this.formatsList= formats;
        this.presenter =presenter;
    }
    //ESTE ES
    @NonNull
    @Override
    public FormatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        FormatCardItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.format_card_item,parent,false);
        return new FormatViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FormatViewHolder holder, int position) {
        FormatEntity format = formatsList.get(position);
        holder.binding.setFormat(format);
        holder.binding.setPresenter(presenter);



    }
    @Override
    public int getItemCount() {
       return  formatsList.size();
    }

    public  static  class FormatViewHolder extends RecyclerView.ViewHolder{

        private final FormatCardItemBinding binding;
        public FormatViewHolder (FormatCardItemBinding binding){
            super(binding.getRoot());
            this.binding= binding;
        }


    }
    public interface  FormatPresenter
    {
        void onClickFormat(FormatEntity format);
    }


}
