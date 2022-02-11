package by.vchaikovski.coffeehouse.model.entity;

import java.time.LocalDate;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Delivery.
 */
public class Delivery extends AbstractEntity {
    /**
     * The enum Delivery type.
     */
    public enum DeliveryType {
        /**
         * Delivery delivery type.
         */
        DELIVERY,
        /**
         * Pick up delivery type.
         */
        PICK_UP
    }

    private DeliveryType deliveryType;
    private LocalDate deliveryDate;
    private long addressId;

    /**
     * Instantiates a new Delivery.
     *
     * @param deliveryType the delivery type
     * @param deliveryDate the delivery date
     * @param addressId    the address id
     */
    public Delivery(DeliveryType deliveryType, LocalDate deliveryDate, long addressId) {
        this.deliveryType = deliveryType;
        this.deliveryDate = deliveryDate;
        this.addressId = addressId;
    }

    /**
     * Instantiates a new Delivery.
     *
     * @param deliveryType the delivery type
     * @param deliveryDate the delivery date
     */
    public Delivery(DeliveryType deliveryType, LocalDate deliveryDate) {
        this.deliveryType = deliveryType;
        this.deliveryDate = deliveryDate;
    }

    /**
     * Gets delivery type.
     *
     * @return the delivery type
     */
    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    /**
     * Sets delivery type.
     *
     * @param deliveryType the delivery type
     */
    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    /**
     * Gets delivery time.
     *
     * @return the delivery time
     */
    public LocalDate getDeliveryTime() {
        return deliveryDate;
    }

    /**
     * Sets delivery date.
     *
     * @param deliveryDate the delivery date
     */
    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * Gets address id.
     *
     * @return the address id
     */
    public long getAddressId() {
        return addressId;
    }

    /**
     * Sets address id.
     *
     * @param addressId the address id
     */
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
                (deliveryDate != null ? deliveryDate.equals(delivery.deliveryDate) : delivery.deliveryDate == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + super.hashCode();
        result = result * first + (deliveryType != null ? deliveryType.hashCode() : 0);
        result = result * first + (deliveryDate != null ? deliveryDate.hashCode() : 0);
        result = result * first + (int) addressId;

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer(super.toString())
                .append(", deliveryType = ")
                .append(deliveryType)
                .append(", deliveryDate = ")
                .append(deliveryDate)
                .append(", addressId = ")
                .append(addressId)
                .append('}')
                .toString();
    }
}