package by.vchaikovski.coffeeshop.model.entity;


import java.util.HashMap;
import java.util.Map;

public class OrderCart extends AbstractEntity {
    private Map<Menu, Integer> cart;
    private long orderId;

    public OrderCart(long orderId, Map<Menu, Integer> cart) {
        this.orderId = orderId;
        this.cart = cart != null ? cart : new HashMap<>();
    }

    @Override
    public void setId(long id) {
        throw new UnsupportedOperationException("The setId(long id) method is not supported.");
    }

    @Override
    public long getId() {
        throw new UnsupportedOperationException("The getId() method is not supported.");
    }

    public Map<Menu, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<Menu, Integer> cart) {
        this.cart = cart;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderCart orderCart = (OrderCart) o;
        return cart.equals(orderCart.cart) && orderId == orderCart.orderId;
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = first + (int) orderId;
        result = result * cart.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuffer orderCart = new StringBuffer("OrderCart{")
                .append("id =")
                .append(orderId);
        cart.forEach((k, v) -> orderCart.append(k)
                .append(", quantity=")
                .append(v)
                .append(", "));
        orderCart.append('}');

        return orderCart.toString();
    }
}