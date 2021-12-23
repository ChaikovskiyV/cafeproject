package by.vchaikovski.coffeeshop.entity;

import java.time.LocalDateTime;

public class Delivery extends AbstractEntity {
    public enum DeliveryType {DELIVERY, PICK_UP}

    private long id;
    private DeliveryType deliveryType;
    private LocalDateTime deliveryTime;
    private AddressDelivery address;

    public Delivery() {
        deliveryType = DeliveryType.PICK_UP;
    }

    public Delivery(LocalDateTime deliveryTime, AddressDelivery address) {
        deliveryType = DeliveryType.DELIVERY;
        this.deliveryTime = deliveryTime;
        this.address = address;
    }

    public Delivery(LocalDateTime deliveryTime) {
        deliveryType = DeliveryType.PICK_UP;
        this.deliveryTime = deliveryTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public AddressDelivery getAddress() {
        return address;
    }

    public void setAddress(AddressDelivery address) {
        if (deliveryType == DeliveryType.PICK_UP) {
            deliveryType = DeliveryType.DELIVERY;
        }
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return id == delivery.id && deliveryType == delivery.deliveryType &&
                (deliveryTime != null ? deliveryTime.equals(delivery.deliveryTime) : delivery.deliveryTime == null) &&
                (address != null ? address.equals(delivery.address) : delivery.address == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + (int) id;
        result = result * first + (deliveryType != null ? deliveryType.hashCode() : 0);
        result = result * first + (deliveryTime != null ? deliveryTime.hashCode() : 0);
        result = result * first + (address != null ? address.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer("Delivery{")
                .append("id=")
                .append(id)
                .append(", deliveryType=")
                .append(deliveryType)
                .append(", deliveryTime=")
                .append(deliveryTime)
                .append(", address=")
                .append(address)
                .append('}')
                .toString();
    }
}