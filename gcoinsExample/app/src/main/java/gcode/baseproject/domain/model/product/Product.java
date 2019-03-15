package gcode.baseproject.domain.model.product;

import androidx.databinding.ObservableField;

public class Product {

    private  String Id;
    private  String Name;
    private  int Quantity;
    private  int Price;
    public final ObservableField<Boolean> enabled= new ObservableField<>();

    public  Product(String id, String name, int quantity, int price){
        this.Id = id;
        this.Name= name;
        this.Quantity=quantity;
        this.Price = price;
        enabled.set(true);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

}
