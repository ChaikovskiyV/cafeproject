package by.vchaikovski.coffeeshop.model.entity;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Menu extends AbstractEntity {
    public enum FoodType {BEVERAGE, SUPPLEMENT, PASTRY}

    private String name;
    private FoodType type;
    private BigDecimal price;
    private int quantityInStock;
    private LocalDate productionDate;
    private LocalDate expirationDate;
    private String description;
    private transient Image foodImage;

    public Menu(MenuBuilder builder) {
        super.setId(builder.id);
        name = builder.name;
        type = builder.type;
        price = builder.price;
        quantityInStock = builder.quantityInStock; //TODO consider maybe it's excess
        productionDate = builder.productionDate;
        expirationDate = builder.expirationDate;
        description = builder.description;
        foodImage = builder.foodImage;
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

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setFoodQuantity(int quantityInStock) {
        this.quantityInStock = quantityInStock;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return super.equals(menu) && quantityInStock == menu.quantityInStock && type == menu.type &&
                (name != null ? name.equals(menu.name) : menu.name == null) &&
                (price != null ? price.equals(menu.price) : menu.price == null) &&
                (productionDate != null ? productionDate.equals(menu.productionDate) : menu.productionDate == null) &&
                (expirationDate != null ? expirationDate.equals(menu.expirationDate) : menu.expirationDate == null) &&
                (description != null ? description.equals(menu.description) : menu.description == null) &&
                (foodImage != null ? foodImage.equals(menu.foodImage) : menu.foodImage == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + super.hashCode();
        result = result * first + (name != null ? name.hashCode() : 0);
        result = result * first + (type != null ? type.hashCode() : 0);
        result = result * first + (price != null ? price.hashCode() : 0);
        result = result * first * quantityInStock;
        result = result * first + (productionDate != null ? productionDate.hashCode() : 0);
        result = result * first + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = result * first + (description != null ? description.hashCode() : 0);
        result = result * first + (foodImage != null ? foodImage.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer(super.hashCode())
                .append(", name = ")
                .append(name)
                .append(", type = ")
                .append(type)
                .append(", price = ")
                .append(price)
                .append(", quantityInStock = ") //TODO consider maybe it's excess
                .append(quantityInStock)
                .append(", productionDate = ")
                .append(productionDate)
                .append(", expirationDate = ")
                .append(expirationDate)
                .append(", description = ")
                .append(description)
                .append('}')
                .toString();
    }

    public static class MenuBuilder {
        private long id;
        private String name;
        private FoodType type;
        private BigDecimal price;
        private int quantityInStock;
        private LocalDate productionDate;
        private LocalDate expirationDate;
        private String description;
        private Image foodImage;

        public MenuBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public MenuBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public MenuBuilder setType(FoodType type) {
            this.type = type;
            return this;
        }

        public MenuBuilder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public MenuBuilder setQuantityInStock(int quantityInStock) {
            this.quantityInStock = quantityInStock;
            return this;
        }

        public MenuBuilder setProductionDate(LocalDate productionDate) {
            this.productionDate = productionDate;
            return this;
        }

        public MenuBuilder setExpirationDate(LocalDate expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public MenuBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public MenuBuilder setFoodImage(Image foodImage) {
            this.foodImage = foodImage;
            return this;
        }

        public Menu build() {
            return new Menu(this);
        }
    }
}