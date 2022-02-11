package by.vchaikovski.coffeehouse.model.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Food order.
 */
public class FoodOrder extends AbstractEntity {
    /**
     * The enum Order status.
     */
    public enum OrderStatus {
        /**
         * Waiting order status.
         */
        WAITING,
        /**
         * Accepted order status.
         */
        ACCEPTED,
        /**
         * Cancelled order status.
         */
        CANCELLED,
        /**
         * Ready order status.
         */
        READY,
        /**
         * Completed order status.
         */
        COMPLETED
    }

    /**
     * The enum Order evaluation.
     */
    public enum OrderEvaluation {
        /**
         * Bad order evaluation.
         */
        BAD,
        /**
         * Nice order evaluation.
         */
        NICE,
        /**
         * Great order evaluation.
         */
        GREAT,
        /**
         * Brilliant order evaluation.
         */
        BRILLIANT,
        /**
         * No evaluation order evaluation.
         */
        NO_EVALUATION
    }

    private OrderStatus status;
    private LocalDate creationDate;
    private String comment;
    private OrderEvaluation evaluation;
    private Map<Menu, Integer> cart;
    private long deliveryId;
    private long billId;
    private long userId;

    /**
     * Instantiates a new Food order.
     *
     * @param builder the builder
     */
    public FoodOrder(FoodOrderBuilder builder) {
        super.setId(builder.id);
        status = builder.status != null ? builder.status : OrderStatus.WAITING;
        creationDate = builder.creationDate != null ? builder.creationDate : LocalDate.now();
        comment = builder.comment;
        evaluation = builder.evaluation != null ? builder.evaluation : OrderEvaluation.NO_EVALUATION;
        cart = builder.cart != null ? builder.cart : new HashMap<>();
        deliveryId = builder.deliveryId;
        billId = builder.billId;
        userId = builder.userId;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets creation date.
     *
     * @param creationDate the creation date
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets evaluation.
     *
     * @return the evaluation
     */
    public OrderEvaluation getEvaluation() {
        return evaluation;
    }

    /**
     * Sets evaluation.
     *
     * @param evaluation the evaluation
     */
    public void setEvaluation(OrderEvaluation evaluation) {
        this.evaluation = evaluation;
    }

    /**
     * Gets cart.
     *
     * @return the cart
     */
    public Map<Menu, Integer> getCart() {
        return cart;
    }

    /**
     * Sets cart.
     *
     * @param cart the cart
     */
    public void setCart(Map<Menu, Integer> cart) {
        this.cart = cart;
    }

    /**
     * Gets delivery id.
     *
     * @return the delivery id
     */
    public long getDeliveryId() {
        return deliveryId;
    }

    /**
     * Sets delivery id.
     *
     * @param deliveryId the delivery id
     */
    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    /**
     * Gets bill id.
     *
     * @return the bill id
     */
    public long getBillId() {
        return billId;
    }

    /**
     * Sets bill id.
     *
     * @param billId the bill id
     */
    public void setBillId(long billId) {
        this.billId = billId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodOrder foodOrder = (FoodOrder) o;
        return super.equals(foodOrder) && status == foodOrder.status && evaluation == foodOrder.evaluation &&
                deliveryId == foodOrder.deliveryId && billId == foodOrder.billId && userId == foodOrder.userId &&
                (creationDate != null ? creationDate.equals(foodOrder.creationDate) : foodOrder.creationDate == null) &&
                (comment != null ? comment.equals(foodOrder.comment) : foodOrder.comment == null) &&
                (cart != null ? cart.equals(foodOrder.cart) : foodOrder.cart == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + super.hashCode();
        result = result * first + (status != null ? status.hashCode() : 0);
        result = result * first + (creationDate != null ? creationDate.hashCode() : 0);
        result = result * first + (comment != null ? comment.hashCode() : 0);
        result = result * first + (evaluation != null ? evaluation.hashCode() : 0);
        result = result * first + (cart != null ? cart.hashCode() : 0);
        result = result * first + (int) deliveryId;
        result = result * first + (int) billId;
        result = result * first + (int) userId;

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer(super.toString())
                .append(", status = ")
                .append(status)
                .append(", creationDate = ")
                .append(creationDate)
                .append(", userId = ")
                .append(userId)
                .append(", comment = ")
                .append(comment)
                .append(", cart = ")
                .append(cart)
                .append(", deliveryId = ")
                .append(deliveryId)
                .append(", billId = ")
                .append(billId)
                .append("', evaluation = ")
                .append(evaluation)
                .append('}')
                .toString();
    }

    /**
     * The type Food order builder.
     */
    public static class FoodOrderBuilder {
        private long id;
        private OrderStatus status;
        private LocalDate creationDate;
        private String comment;
        private OrderEvaluation evaluation;
        private Map<Menu, Integer> cart;
        private long deliveryId;
        private long billId;
        private long userId;

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public FoodOrderBuilder setId(long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets status.
         *
         * @param status the status
         * @return the status
         */
        public FoodOrderBuilder setStatus(OrderStatus status) {
            this.status = status;
            return this;
        }

        /**
         * Sets creation date.
         *
         * @param creationDate the creation date
         * @return the creation date
         */
        public FoodOrderBuilder setCreationDate(LocalDate creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        /**
         * Sets comment.
         *
         * @param comment the comment
         * @return the comment
         */
        public FoodOrderBuilder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        /**
         * Sets evaluation.
         *
         * @param evaluation the evaluation
         * @return the evaluation
         */
        public FoodOrderBuilder setEvaluation(OrderEvaluation evaluation) {
            this.evaluation = evaluation;
            return this;
        }

        /**
         * Sets cart.
         *
         * @param cart the cart
         * @return the cart
         */
        public FoodOrderBuilder setCart(Map<Menu, Integer> cart) {
            this.cart = cart;
            return this;
        }

        /**
         * Sets delivery id.
         *
         * @param deliveryId the delivery id
         * @return the delivery id
         */
        public FoodOrderBuilder setDeliveryId(long deliveryId) {
            this.deliveryId = deliveryId;
            return this;
        }

        /**
         * Sets bill id.
         *
         * @param billId the bill id
         * @return the bill id
         */
        public FoodOrderBuilder setBillId(long billId) {
            this.billId = billId;
            return this;
        }

        /**
         * Sets user id.
         *
         * @param userId the user id
         * @return the user id
         */
        public FoodOrderBuilder setUserId(long userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Build food order.
         *
         * @return the food order
         */
        public FoodOrder build() {
            return new FoodOrder(this);
        }
    }
}