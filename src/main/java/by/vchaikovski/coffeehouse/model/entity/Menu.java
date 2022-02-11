package by.vchaikovski.coffeehouse.model.entity;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Menu.
 */
public class Menu extends AbstractEntity {
    /**
     * The enum Food type.
     */
    public enum FoodType {
        /**
         * Coffee food type.
         */
        COFFEE,
        /**
         * Tea food type.
         */
        TEA,
        /**
         * Pastry food type.
         */
        PASTRY
    }

    private String name;
    private FoodType type;
    private BigDecimal price;
    private int quantityInStock;
    private String description;
    private byte[] image;

    /**
     * Instantiates a new Menu.
     *
     * @param builder the builder
     */
    public Menu(MenuBuilder builder) {
        super.setId(builder.id);
        name = builder.name;
        type = builder.type;
        price = builder.price;
        quantityInStock = builder.quantityInStock;
        description = builder.description;
        image = builder.image;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public FoodType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(FoodType type) {
        this.type = type;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets quantity in stock.
     *
     * @return the quantity in stock
     */
    public int getQuantityInStock() {
        return quantityInStock;
    }

    /**
     * Sets quantity in stock.
     *
     * @param quantityInStock the quantity in stock
     */
    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get image byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
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

    /**
     * The type Menu builder.
     */
    public static class MenuBuilder {
        private long id;
        private String name;
        private FoodType type;
        private BigDecimal price;
        private int quantityInStock;
        private String description;
        private byte[] image;

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public MenuBuilder setId(long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets name.
         *
         * @param name the name
         * @return the name
         */
        public MenuBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets type.
         *
         * @param type the type
         * @return the type
         */
        public MenuBuilder setType(FoodType type) {
            this.type = type;
            return this;
        }

        /**
         * Sets price.
         *
         * @param price the price
         * @return the price
         */
        public MenuBuilder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        /**
         * Sets quantity in stock.
         *
         * @param quantityInStock the quantity in stock
         * @return the quantity in stock
         */
        public MenuBuilder setQuantityInStock(int quantityInStock) {
            this.quantityInStock = quantityInStock;
            return this;
        }

        /**
         * Sets description.
         *
         * @param description the description
         * @return the description
         */
        public MenuBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets food image.
         *
         * @param image the image
         * @return the food image
         */
        public MenuBuilder setFoodImage(byte[] image) {
            this.image = image;
            return this;
        }

        /**
         * Build menu.
         *
         * @return the menu
         */
        public Menu build() {
            return new Menu(this);
        }
    }
}