package by.vchaikovski.coffeeshop.entity;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OrderCart extends AbstractEntity{
    static final Logger logger = LogManager.getLogger();
    private Map<Menu, Integer> cart;

    public OrderCart() {
        cart = new HashMap<>();
    }

    public Map<Menu, Integer> getCart() {
        return cart;
    }

    public void addProduct(Menu menu) {
        if (cart.containsKey(menu)) {
            int number = cart.get(menu);
            cart.put(menu, ++number);
        } else {
            cart.put(menu, 1);
        }
    }

    public void reduceNumber(Menu menu) {
        if (cart.containsKey(menu)) {
            int number = cart.get(menu);
            if (number > 1) {
                cart.put(menu, --number);
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

    public long findProductsNumber() {
        return cart.values().stream().count();
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