package by.vchaikovski.coffeshop.entity;

import java.util.concurrent.atomic.AtomicLong;

public class Delivery {
    public enum DeliveryType {DELIVERY, PICK_UP}

    private static AtomicLong atomicId = new AtomicLong(0);
    private long id;
    private DeliveryType deliveryType;
    private AddressDelivery address;

    public Delivery(DeliveryType deliveryType, AddressDelivery address) {
        this.deliveryType = deliveryType;
        this.address = address;
    }

    public Delivery(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public long getId() {
        return id;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public AddressDelivery getAddress() {
        return address;
    }

    public void setAddress(AddressDelivery address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return id == delivery.id && deliveryType == delivery.deliveryType &&
                address != null ? address.equals(delivery.address) : delivery.address == null;
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first * (int) id;
        result = result * first + (deliveryType != null ? deliveryType.hashCode() : 0);
        result = result * first + (address != null ? address.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder("Delivery{")
                .append("id=")
                .append(id)
                .append(", deliveryType=")
                .append(deliveryType)
                .append(", address=")
                .append(address)
                .append('}')
                .toString();
    }
}