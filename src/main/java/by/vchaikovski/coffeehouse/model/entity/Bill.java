package by.vchaikovski.coffeehouse.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Bill.
 */
public class Bill extends AbstractEntity {
    /**
     * The enum Bill status.
     */
    public enum BillStatus {
        /**
         * Paid bill status.
         */
        PAID,
        /**
         * Not paid bill status.
         */
        NOT_PAID
    }

    private BigDecimal totalPrice;
    private LocalDate paymentDate;
    private BillStatus status;

    /**
     * Instantiates a new Bill.
     *
     * @param status      the status
     * @param paymentDate the payment date
     * @param totalPrice  the total price
     */
    public Bill(BillStatus status, LocalDate paymentDate, BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        this.paymentDate = paymentDate;
        this.status = status != null ? status : BillStatus.NOT_PAID;
    }

    /**
     * Instantiates a new Bill.
     *
     * @param totalPrice the total price
     */
    public Bill(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        this.status = BillStatus.NOT_PAID;
    }

    /**
     * Gets total price.
     *
     * @return the total price
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets total price.
     *
     * @param totalPrice the total price
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets payment date.
     *
     * @return the payment date
     */
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets payment date.
     *
     * @param paymentDate the payment date
     */
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public BillStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(BillStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return super.equals(bill) && status == bill.status &&
                (totalPrice != null ? totalPrice.equals(bill.totalPrice) : bill.totalPrice == null) &&
                (paymentDate != null ? paymentDate.equals(bill.paymentDate) : bill.paymentDate == null);
    }

    @Override
    public int hashCode() {
        final int first = 31;
        int result = 1;
        result = result * first + super.hashCode();
        result = result * first + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = result * first + (paymentDate != null ? paymentDate.hashCode() : 0);
        result = result * first + (status != null ? status.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer(super.toString())
                .append(", totalPrice = ")
                .append(totalPrice)
                .append(", paymentDate = ")
                .append(paymentDate)
                .append(", status = ")
                .append(status)
                .append('}')
                .toString();
    }
}