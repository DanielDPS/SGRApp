package gcode.baseproject.interactors.adapters;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.CustomerCardItemBinding;
import gcode.baseproject.interactors.db.entities.CustomerEntity;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<CustomerEntity> customerEntities;
    private LayoutInflater layoutInflater;
    private  CustomersPresenter presenter;
    private  CustomerCardItemBinding mybinding;
    public  CustomerAdapter(List<CustomerEntity > entities ,CustomersPresenter presenter){
         this.customerEntities= entities;
        this.presenter =presenter;

    }


    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater= LayoutInflater.from(parent.getContext());
        }
        CustomerCardItemBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.customer_card_item,parent,false);
        this.mybinding = binding;
        return new CustomerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        CustomerEntity customerEntity = customerEntities.get(position);
        holder.binding.setCustomer(customerEntity);
        holder.binding.setPresenter(presenter);
    }
    public  CustomerCardItemBinding getMybinding(){
        return this.mybinding;
    }

    @Override
    public int getItemCount() {
        return customerEntities.size();
    }

    public  List<CustomerEntity> getCustomerEntities (){return this.customerEntities;}
    public CustomersPresenter getPresenter(){return this.presenter;}

    public static class CustomerViewHolder extends RecyclerView.ViewHolder{

        private CustomerCardItemBinding binding;
        private ColorGenerator colorGenerator;

        public  CustomerViewHolder(CustomerCardItemBinding binding){
            super(binding.getRoot());
            colorGenerator= ColorGenerator.MATERIAL;
            int color = colorGenerator.getColor(Color.rgb(129,156,132));
            this.binding = binding;
            Drawable drawable= TextDrawable.builder().buildRound("GA",color);
            binding.customerImg.setImageDrawable(drawable);
            /*Picasso.get()
                    .load(R.drawable.ic_customer_identifier)
                    .transform(new CircleTransformation())
                    .into(this.binding.customerImg);*/
        }

    }
    public  interface  CustomersPresenter{
        void OnClickPresenter(CustomerEntity customerEntity);
    }
}
