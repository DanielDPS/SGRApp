package gcode.baseproject.view.viewmodel.product;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import gcode.baseproject.domain.model.product.Product;
import gcode.baseproject.domain.model.product.ShoppingCartComputations;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ShoppingCartViewModel extends BaseNetworkViewModel {


    private  List<Product> productList;

    public ShoppingCartViewModel(@NonNull Application application) {
        super(application);
        productList = new ArrayList<>();
    }

    // Ahora si debe de tener mas sentido, completalo we
    // me imagino que ya mas omenos ubicas de que va
    //haber deja intento completarlo alv nose jaja asignas los valores alli ?
    // Si wes, si te fijas como el nombre sujiere este metodo se encarga de hacer los calculos
    // de la cantidad total de productos , como un carrito
    // la clase shoping cart computations es para guardar los calculos y mostrarlos
    // si te estoy enredando dime
    //ok si si entiendo hay me supongo que harias un acumulador o por ser el for te dara
    //el total? si exacto, es un acumulador, por eso el for
    // y por eso el single, estas cositas son mas pesadas de lo que parence XD
    //simon pero hay donde usas el objeto ShoppingCartComputations sus propiedades digo
    // Para eso son primeros los calculos, ya que calcules lo asignas al objeto, el objeto solo
    // es una clase contenedora
    //intentare hacerlo arre ese pedaso ? asi quedaria mas o menos ?
    //y despues de esto que sigue usar el view model supongo no ?,
    // No, ese metodo no esta terminado, terminalo, que crees que siga?
    //Usar el ObserverField ?
    // No usar el single, que si mandara a llamar el observableField
    ///alv jaja

    private void computeShoppingCart() {
        Single<ShoppingCartComputations> single  = Single.fromCallable(new Callable<ShoppingCartComputations>() {
            @Override
            public ShoppingCartComputations call() throws Exception {
                int totalQuantity = 0;
                float totalSubtotal = 0;
                for (Product product : productList) {
                    totalQuantity+= product.getQuantity();
                    totalSubtotal+=product.getPrice()* product.getQuantity();
                }

                ShoppingCartComputations shoppingCartC = new ShoppingCartComputations();
                shoppingCartC.setQuantity(totalQuantity);
                shoppingCartC.setSubtotal(totalSubtotal);

                return shoppingCartC;
            }
        });
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableSingleObserver());
    }
    //algo asi o que jajaj
    // Si we, ahora que vas a hacer?
    //subscribirlo al single de arriba deja analizo bien
    private DisposableSingleObserver<ShoppingCartComputations> disposableSingleObserver (){
        return new DisposableSingleObserver<ShoppingCartComputations>() {
            @Override
            public void onSuccess(ShoppingCartComputations shoppingCartComputations) {
                 mshoppingCartComputations.postValue(shoppingCartComputations);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        };
    }


    public void addAll(List<Product> products){
        productList.addAll(products);
        computeShoppingCart();
    }


    // Esto es para observer en el fragment cuando el carrito tenga cambos
    private MutableLiveData<ShoppingCartComputations> mshoppingCartComputations = new MutableLiveData<>();


    public MutableLiveData<ShoppingCartComputations> getShoppingCartComputations() {
        return mshoppingCartComputations;
    }
}
