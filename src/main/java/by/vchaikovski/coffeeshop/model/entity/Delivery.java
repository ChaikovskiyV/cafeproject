package by.vchaikovski.coffeeshop.model.entity;

import java.time.LocalDateTime;

public class Delivery extends AbstractEntity {
    public enum DeliveryType {DELIVERY, PICK_UP}

    private DeliveryType deliveryType;
    private LocalDateTime deliveryTime;
    private long addressId;

    public Delivery(DeliveryType deliveryType, LocalDateTime deliveryTime, long addressId) {
        this.deliveryType = deliveryType;
        this.deliveryTime = deliveryTime;
        this.addressId = addressId;
    }

    public Delivery(DeliveryType deliveryType, LocalDateTime deliveryTime) {
        this.deliveryType = deliveryType;
        this.deliveryTime = deliveryTime;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        if (deliveryType == DeliveryType.PICK_UP) {
            deliveryType = DeliveryType.DELIVERY;
        }
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return super.equals(delivery) && deliveryType == delivery.deliveryType && addressId == delivery.addressId &&
                (deliveryTime != null ? deliveryTime.equals(delivery.deliveryTime) : delivery.deliveryTime == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + super.hashCode();
        result = result * first + (deliveryType != null ? deliveryType.hashCode() : 0);
        result = result * first + (deliveryTime != null ? deliveryTime.hashCode() : 0);
        result = result * first + (int) addressId;

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer(super.toString())
                .append(", deliveryType = ")
                .append(deliveryType)
                .append(", deliveryTime = ")
                .append(deliveryTime)
                .append(", addressId = ")
                .append(addressId)
                .append('}')
                .toString();
    }
}