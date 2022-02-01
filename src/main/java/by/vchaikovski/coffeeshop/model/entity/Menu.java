package by.vchaikovski.coffeeshop.model.entity;

import java.math.BigDecimal;
import java.util.Arrays;

public class Menu extends AbstractEntity {
    public enum FoodType {COFFEE, TEA, PASTRY}

    private String name;
    private FoodType type;
    private BigDecimal price;
    private int quantityInStock;
    private String description;
    private byte[] image;

    public Menu(MenuBuilder builder) {
        super.setId(builder.id);
        name = builder.name;
        type = builder.type;
        price = builder.price;
        quantityInStock = builder.quantityInStock;
        description = builder.description;
        image = builder.image;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return super.equals(menu) && quantityInStock == menu.quantityInStock && type == menu.type &&
                (name != null ? name.equals(menu.name) : menu.name == null) &&
                (price != null ? price.equals(menu.price) : menu.price == null) &&
                (description != null ? description.equals(menu.description) : menu.description == null) &&
                (image != null ? Arrays.equals(image, menu.image) : menu.image == null);
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
        result = result * first + (description != null ? description.hashCode() : 0);
        result = result * first + (image != null ? Arrays.hashCode(image) : 0);

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
                .append(", quantityInStock = ")
                .append(quantityInStock)
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
        private String description;
        private byte[] image;

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

        public MenuBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public MenuBuilder setFoodImage(byte[] image) {
            this.image = image;
            return this;
        }

        public Menu build() {
            return new Menu(this);
        }
    }
}