package gcode.baseproject.domain.model.product;

public class ShoppingCartComputations {

    private  int Quantity;
    private  float Subtotal;

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public float getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(float subtotal) {
        Subtotal = subtotal;
    }
}
