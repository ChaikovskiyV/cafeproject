package by.vchaikovski.coffeeshop.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill extends AbstractEntity {
    public enum BillStatus {PAID, NOT_PAID}

    private BigDecimal totalPrice;
    private LocalDateTime paymentDate;
    private BillStatus status;

    public Bill(BillStatus status, LocalDateTime paymentDate, BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        this.paymentDate = paymentDate;
        this.status = status != null ? status : BillStatus.NOT_PAID;
    }

    public Bill(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        this.status = BillStatus.NOT_PAID;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BillStatus getStatus() {
        return status;
    }

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