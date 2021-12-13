package by.vchaikovski.coffeeshop.entity;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Menu {
    public enum FoodType {BEVERAGE, SUPPLEMENT, PASTRY}

    private long id;
    private String name;
    private FoodType type;
    private BigDecimal price;
    private int foodQuantity;
    private LocalDate productionDate;
    private LocalDate expirationDate;
    private Image foodImage;

    public Menu(String name, FoodType type, BigDecimal price, int foodQuantity, LocalDate productionDate, LocalDate expirationDate, Image foodImage) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.foodQuantity = foodQuantity;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.foodImage = foodImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Image getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(Image foodImage) {
        this.foodImage = foodImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return id == menu.id && foodQuantity == menu.foodQuantity && type == menu.type &&
                (name != null ? name.equals(menu.name) : menu.name == null) &&
                (price != null ? price.equals(menu.price) : menu.price == null) &&
                (productionDate != null ? productionDate.equals(menu.productionDate) : menu.productionDate == null) &&
                (expirationDate != null ? expirationDate.equals(menu.expirationDate) : menu.expirationDate == null) &&
                (foodImage != null ? foodImage.equals(menu.foodImage) : menu.foodImage == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + (id != 0 ? (int) id : 0);
        result = result * first + (name != null ? name.hashCode() : 0);
        result = result * first + (type != null ? type.hashCode() : 0);
        result = result * first + (price != null ? price.hashCode() : 0);
        result = result * first * foodQuantity;
        result = result * first + (productionDate != null ? productionDate.hashCode() : 0);
        result = result * first + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = result * first + (foodImage != null ? foodImage.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder("Food{")
                .append("id=")
                .append(id)
                .append(", name='")
                .append(name)
                .append(", type=")
                .append(type)
                .append(", price=")
                .append(price)
                .append(", foodQuantity=")
                .append(foodQuantity)
                .append(", productionDate=")
                .append(productionDate)
                .append(", expirationDate=")
                .append(expirationDate)
                .append('}')
                .toString();
    }
}