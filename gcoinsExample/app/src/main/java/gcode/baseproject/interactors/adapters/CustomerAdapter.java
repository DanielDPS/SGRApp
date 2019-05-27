package gcode.baseproject.interactors.adapters;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.CustomerCardItemBinding;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.image.ImageHelper;
import gcode.baseproject.view.ui.Customer.CustomerFragment;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<CustomerEntity> customerEntities;
    private LayoutInflater layoutInflater;
    private  CustomersPresenter presenter;
    private  CustomerCardItemBinding mybinding;
    private CustomerFragment fragment;
    public  CustomerAdapter(List<CustomerEntity > entities ,CustomersPresenter presenter,CustomerFragment fragment){
         this.customerEntities= entities;
        this.presenter =presenter;
        this.fragment = fragment;
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


         ColorGenerator colorGenerator;
         colorGenerator= ColorGenerator.MATERIAL;
        int color = colorGenerator.getRandomColor();
        Drawable drawable= TextDrawable.builder().buildRound(String.valueOf(customerEntity.getCBusinessName().charAt(0)),color);
        holder.binding.customerImg.setImageDrawable(drawable);


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

        public  CustomerViewHolder(CustomerCardItemBinding binding){
            super(binding.getRoot());
          this.binding = binding;


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
