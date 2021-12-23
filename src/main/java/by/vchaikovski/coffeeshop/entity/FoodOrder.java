package by.vchaikovski.coffeeshop.entity;

import java.time.LocalDateTime;

public class FoodOrder extends AbstractEntity {
    public enum OrderStatus {WAITING, ACCEPTED, REJECTED, PREPARING, READY, DELIVERED, COMPLETED}

    public enum OrderEvaluation {BAD, NOT_BAD, NICE, GREAT, BRILLIANT}

    private long id;
    private OrderStatus status;
    private LocalDateTime creationDate;
    private String comment;
    private OrderEvaluation evaluation;
    private OrderCart cart;
    private Delivery delivery;
    private Bill bill;
    private User user;

    public FoodOrder() {
    }

    public FoodOrder(User user, Delivery delivery, Bill bill) {
        this.status = OrderStatus.WAITING;
        creationDate = LocalDateTime.now();
        this.user = user;
        this.delivery = delivery;
        this.bill = bill;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodOrder foodOrder = (FoodOrder) o;
        return id == foodOrder.id && status == foodOrder.status && evaluation == foodOrder.evaluation &&
                (creationDate != null ? creationDate.equals(foodOrder.creationDate) : foodOrder.creationDate == null) &&
                (comment != null ? comment.equals(foodOrder.comment) : foodOrder.comment == null) &&
                (cart != null ? cart.equals(foodOrder.cart) : foodOrder.cart == null) &&
                (delivery != null ? delivery.equals(foodOrder.delivery) : foodOrder.delivery == null) &&
                (bill != null ? bill.equals(foodOrder.bill) : foodOrder.bill == null) &&
                (user != null ? user.equals(foodOrder.user) : foodOrder.user == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + (int) id;
        result = result * first + (status != null ? status.hashCode() : 0);
        result = result * first + (creationDate != null ? creationDate.hashCode() : 0);
        result = result * first + (comment != null ? comment.hashCode() : 0);
        result = result * first + (evaluation != null ? evaluation.hashCode() : 0);
        result = result * first + (cart != null ? cart.hashCode() : 0);
        result = result * first + (delivery != null ? delivery.hashCode() : 0);
        result = result * first + (bill != null ? bill.hashCode() : 0);
        result = result * first + (user != null ? user.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer("FoodOrder{")
                .append("id=")
                .append(id)
                .append(", status=")
                .append(status)
                .append(", creationDate=")
                .append(creationDate)
                .append(", user=")
                .append(user)
                .append(", comment='")
                .append(comment)
                .append(", cart=")
                .append(cart)
                .append(", delivery=")
                .append(delivery)
                .append(", bill=")
                .append(bill)
                .append("', evaluation=")
                .append(evaluation)
                .append('}')
                .toString();
    }
}