package by.vchaikovski.coffeeshop.entity;

import java.time.LocalDateTime;

public class Delivery extends AbstractEntity {
    public enum DeliveryType {DELIVERY, PICK_UP}

    private DeliveryType deliveryType;
    private LocalDateTime deliveryTime;
    private AddressDelivery address;

    public Delivery(DeliveryBuilder builder) {
        if (builder == null || !builder.isValid()) {
            String message = "The builder " + builder + " is not valid.";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        super.setId(builder.id);
        deliveryType = builder.deliveryType;
        deliveryTime = builder.deliveryTime;
        address = builder.address;
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
        return super.equals(delivery) && deliveryType == delivery.deliveryType &&
                (deliveryTime != null ? deliveryTime.equals(delivery.deliveryTime) : delivery.deliveryTime == null) &&
                (address != null ? address.equals(delivery.address) : delivery.address == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + super.hashCode();
        result = result * first + (deliveryType != null ? deliveryType.hashCode() : 0);
        result = result * first + (deliveryTime != null ? deliveryTime.hashCode() : 0);
        result = result * first + (address != null ? address.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer(super.toString())
                .append(", deliveryType = ")
                .append(deliveryType)
                .append(", deliveryTime = ")
                .append(deliveryTime)
                .append(", address = ")
                .append(address)
                .append('}')
                .toString();
    }

    public static class DeliveryBuilder {
        private long id;
        private DeliveryType deliveryType;
        private LocalDateTime deliveryTime;
        private AddressDelivery address;

        public DeliveryBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public DeliveryBuilder setDeliveryType(DeliveryType deliveryType) {
            this.deliveryType = deliveryType;
            return this;
        }

        public DeliveryBuilder setDeliveryTime(LocalDateTime deliveryTime) {
            this.deliveryTime = deliveryTime;
            return this;
        }

        public DeliveryBuilder setAddress(AddressDelivery address) {
            this.address = address;
            return this;
        }

        public boolean isValid() {
            return deliveryType != null && deliveryTime != null;
        }

        public Delivery build() {
            return new Delivery(this);
        }
    }
}