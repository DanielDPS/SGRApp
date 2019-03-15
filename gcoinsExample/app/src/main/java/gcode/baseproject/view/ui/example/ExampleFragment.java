package gcode.baseproject.view.ui.example;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.databinding.ExampleFragmentBinding;
import gcode.baseproject.domain.model.product.Product;
import gcode.baseproject.domain.model.product.ShoppingCartComputations;
import gcode.baseproject.interactors.adapters.ProductAdapter;
import gcode.baseproject.interactors.adapters.SwipeToDeleteCallback;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.viewmodel.product.ShoppingCartInfoViewModel;
import gcode.baseproject.view.viewmodel.product.ShoppingCartViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;

public class ExampleFragment  extends BaseFragment implements ProductAdapter.OnProductClickListener {


    ExampleFragmentBinding  binding;
    private ShoppingCartViewModel shoppingCartViewModel;
    private  ShoppingCartInfoViewModel shoppingCartInfoViewModel;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shoppingCartViewModel = ViewModelProviders.of(this).get(ShoppingCartViewModel.class);
        shoppingCartInfoViewModel = ViewModelProviders.of(this).get(ShoppingCartInfoViewModel.class);
        binding.setInfoView(shoppingCartInfoViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ExampleFragmentBinding.inflate(inflater,container,false);
        return  binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        final List<Product> productList = new ArrayList<Product>();
        productList.add(new Product(UUID.randomUUID().toString(),"Tomate salades",5,35));
        productList.add(new Product(UUID.randomUUID().toString(),"Aguacate",15,22));
        productList.add(new Product(UUID.randomUUID().toString(),"Papa blanca",30,25));
        productList.add(new Product(UUID.randomUUID().toString(),"Cebolla blanca",10,21));
        productList.add(new Product(UUID.randomUUID().toString(),"Cebolla morada",4,24));

        binding.exampleRecycler.setHasFixedSize(true);
        binding.exampleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        final ProductAdapter productAdapter = new ProductAdapter(productList,this);
        binding.exampleRecycler.setAdapter(productAdapter);

        SwipeToDeleteCallback swipeCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Obtienes el indice del elemento que le hiciste el swipe
                int index  = viewHolder.getAdapterPosition();

                // Con el indice obtienes el objeto
                Product product = productList.get(index);
                //
                product.enabled.set(false);


                // Esto es para quitar el efecto del swipe
                productAdapter.notifyItemChanged(index);

            }
        };

        // Con esto lo vinculamos al recycler , y listo we
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(binding.exampleRecycler);

        shoppingCartViewModel.getShoppingCartComputations()
                .observe(this, new Observer<ShoppingCartComputations>() {
                    @Override
                    public void onChanged(ShoppingCartComputations shoppingCartComputations) {

                        shoppingCartInfoViewModel.info.set("Cantidad"+String.valueOf(shoppingCartComputations.getQuantity())+"Subtotal"+String.valueOf(shoppingCartComputations.getSubtotal()));
                    }
                });
        shoppingCartViewModel.addAll(productList);




    }

    @NonNull
    @Override
    public String getFragmentTag() {
        return ExampleFragment.class.getName();
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(binding.toolbar)
                .withTitle("EJEMPLO")
                  .withBackButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                }).build();

        return builder;
    }

    @Override
    public void onProductClick(Product product) {

    }
}
