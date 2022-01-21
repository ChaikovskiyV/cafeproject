package by.vchaikovski.coffeeshop.model.entity;

import java.time.LocalDateTime;

public class FoodOrder extends AbstractEntity {
    public enum OrderStatus {WAITING, ACCEPTED, CANCELLED, READY, COMPLETED}

    public enum OrderEvaluation {BAD, NICE, GREAT, BRILLIANT}

    private OrderStatus status;
    private LocalDateTime creationDate;
    private String comment;
    private OrderEvaluation evaluation;
    private OrderCart cart;
    private long deliveryId;
    private long billId;
    private long userId;

    public FoodOrder(FoodOrderBuilder builder) {
        super.setId(builder.id);
        status = builder.status != null ? builder.status : OrderStatus.WAITING;
        creationDate = builder.creationDate != null ? builder.creationDate : LocalDateTime.now();
        comment = builder.comment;
        evaluation = builder.evaluation;
        cart = builder.cart;
        deliveryId = builder.deliveryId;
        billId = builder.billId;
        userId = builder.userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public OrderEvaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(OrderEvaluation evaluation) {
        this.evaluation = evaluation;
    }

    public OrderCart getCart() {
        return cart;
    }

    public void setCart(OrderCart cart) {
        this.cart = cart;
    }

    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public long getUserId() {
        return userId;
    }

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

    public static class FoodOrderBuilder {
        private long id;
        private OrderStatus status;
        private LocalDateTime creationDate;
        private String comment;
        private OrderEvaluation evaluation;
        private OrderCart cart;
        private long deliveryId;
        private long billId;
        private long userId;

        public FoodOrderBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public FoodOrderBuilder setStatus(OrderStatus status) {
            this.status = status;
            return this;
        }

        public FoodOrderBuilder setCreationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public FoodOrderBuilder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public FoodOrderBuilder setEvaluation(OrderEvaluation evaluation) {
            this.evaluation = evaluation;
            return this;
        }

        public FoodOrderBuilder setCart(OrderCart cart) {
            this.cart = cart;
            return this;
        }

        public FoodOrderBuilder setDeliveryId(long deliveryId) {
            this.deliveryId = deliveryId;
            return this;
        }

        public FoodOrderBuilder setBillId(long billId) {
            this.billId = billId;
            return this;
        }

        public FoodOrderBuilder setUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public FoodOrder build() {
            return new FoodOrder(this);
        }
    }
}