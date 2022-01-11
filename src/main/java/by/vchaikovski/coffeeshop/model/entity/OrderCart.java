package by.vchaikovski.coffeeshop.model.entity;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OrderCart extends AbstractEntity {
    @Override
    public void setId(long id) {
        throw new UnsupportedOperationException("The setId(long id) method is not supported.");
    }

    @Override
    public long getId() {
        throw new UnsupportedOperationException("The getId() method is not supported.");
    }

    private final Map<Menu, Integer> cart;

    public OrderCart() {
        cart = new HashMap<>();
    }

    public Map<Menu, Integer> getCart() {
        return cart;
    }

    public void addProduct(Menu menu, int quantity) {
        if (cart.containsKey(menu)) {
            int number = cart.get(menu);
            cart.put(menu, number + quantity);
        } else {
            cart.put(menu, quantity);
        }
    }

    public void reduceNumber(Menu menu, int quantity) {
        if (cart.containsKey(menu)) {
            int number = cart.get(menu);
            if (number > quantity) {
                cart.put(menu, number - quantity);
            } else {
                cart.remove(menu);
            }
        }
    }

    public void deleteProduct(Menu menu) {
        cart.remove(menu);
    }

    public void clearCart() {
        cart.clear();
    }

    public BigDecimal findTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        cart.forEach((k, v) -> totalPrice.add(k.getPrice().multiply(new BigDecimal(v))));

        return totalPrice;
    }

    public long findProductsNumber() { // TODO review this method
        return cart.values().stream().count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderCart orderCart = (OrderCart) o;
        return cart.equals(orderCart.cart);
    }

    @Override
    public int hashCode() {
        int first = 31;
        return first * cart.hashCode();
    }

    @Override
    public String toString() {
        StringBuffer sB = new StringBuffer("OrderCart{");
        cart.forEach((k, v) -> sB.append(k)
                .append(", quantity=")
                .append(v)
                .append(", "));
        sB.append('}');

        return sB.toString();
    }
}