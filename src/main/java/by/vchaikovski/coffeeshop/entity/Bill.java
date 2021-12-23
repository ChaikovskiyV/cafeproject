package by.vchaikovski.coffeeshop.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill extends AbstractEntity {
    public enum BillStatus {PAID, NOT_PAID}

    private static final Logger logger = LogManager.getLogger();
    private long id;
    private BigDecimal totalPrice;
    private LocalDateTime paymentDate;
    private BillStatus status;

    public Bill(BillBuilder builder) {
        if (builder == null || !builder.isValid()) {
            logger.error(() -> "The builder " + builder + " is not valid.");
            throw new IllegalArgumentException("The builder " + builder + " is not valid.");
        }
        id = builder.id;
        totalPrice = builder.totalPrice;
        paymentDate = builder.paymentDate;
        status = builder.status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return id == bill.id && status == bill.status &&
                (totalPrice != null ? totalPrice.equals(bill.totalPrice) : bill.totalPrice == null) &&
                (paymentDate != null ? paymentDate.equals(bill.paymentDate) : bill.paymentDate == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + (int) id;
        result = result * first + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = result * first + (paymentDate != null ? paymentDate.hashCode() : 0);
        result = result * first + (status != null ? status.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer("Bill{")
                .append("id=")
                .append(id)
                .append(", totalPrice=")
                .append(totalPrice)
                .append(", paymentDate=")
                .append(paymentDate)
                .append(", status=")
                .append(status)
                .append('}')
                .toString();
    }

    public static class BillBuilder {
        private long id;
        private BigDecimal totalPrice;
        private LocalDateTime paymentDate;
        private BillStatus status;

        public BillBuilder() {
        }

        public BillBuilder(long id, BigDecimal totalPrice, LocalDateTime paymentDate, BillStatus status) {
            this.id = id;
            this.totalPrice = totalPrice;
            this.paymentDate = paymentDate;
            this.status = status;
        }

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
            return totalPrice != null && status != null;
        }

        public Bill build() {
            return new Bill(this);
        }
    }
}