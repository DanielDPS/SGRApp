package gcode.baseproject.interactors.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.ProductCardItemBinding;
 import gcode.baseproject.domain.model.product.Product;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private  LayoutInflater inflater;
    private  List<Product> productList;
    private  OnProductClickListener presenter;
    public  ProductAdapter(List<Product> products,OnProductClickListener presenter){
        this.productList = products;
        this.presenter = presenter;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        ProductCardItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.product_card_item,parent,false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.binding.setProduct(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {


        private  ProductCardItemBinding binding;
        public ProductViewHolder(ProductCardItemBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }

    // Bien, ahora remplasaras el codigo que tienes para inflar los objetos y la pasaras el objeto al xml
    //a cabron jaja  a sii yaa
    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
}
