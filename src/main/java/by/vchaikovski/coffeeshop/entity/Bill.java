package by.vchaikovski.coffeeshop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill extends AbstractEntity {
    public enum BillStatus {PAID, NOT_PAID}

    private BigDecimal totalPrice;
    private LocalDateTime paymentDate;
    private BillStatus status;

    public Bill(BillBuilder builder) {
        if (builder == null || !builder.isValid()) {
            String message = "The builder " + builder + " is not valid.";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        super.setId(builder.id);
        totalPrice = builder.totalPrice;
        paymentDate = builder.paymentDate;
        status = builder.status != null ? builder.status : BillStatus.NOT_PAID;
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

    public static class BillBuilder {
        private long id;
        private BigDecimal totalPrice;
        private LocalDateTime paymentDate;
        private BillStatus status;

        public BillBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public BillBuilder setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public BillBuilder setPaymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public BillBuilder setStatus(BillStatus status) {
            this.status = status;
            return this;
        }

        public boolean isValid() {
            return totalPrice != null;
        }

        public Bill build() {
            return new Bill(this);
        }
    }
}