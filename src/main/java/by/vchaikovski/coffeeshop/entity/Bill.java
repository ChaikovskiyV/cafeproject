package by.vchaikovski.coffeeshop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill {
    public enum BillStatus {PAID, NOT_PAID}

    private long id;
    private BigDecimal amount;
    private LocalDateTime dateOfIssue;
    private LocalDateTime dateOfPayment;
    private BillStatus status;

    public Bill(BigDecimal amount, LocalDateTime dateOfIssue) {
        this.amount = amount;
        this.dateOfIssue = dateOfIssue;
        status = BillStatus.NOT_PAID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDateOfIssue() {
        return dateOfIssue;
    }

    public LocalDateTime getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDateTime dateOfPayment) {
        if (dateOfIssue.isAfter(dateOfIssue)) {
            this.dateOfPayment = dateOfPayment;
        }
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
                (amount != null ? amount.equals(bill.amount) : bill.amount == null) &&
                (dateOfIssue != null ? dateOfIssue.equals(bill.dateOfIssue) : bill.dateOfIssue == null) &&
                (dateOfPayment != null ? dateOfPayment.equals(bill.dateOfPayment) : bill.dateOfPayment == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + (int) id;
        result = result * first + (amount != null ? amount.hashCode() : 0);
        result = result * first + (dateOfIssue != null ? dateOfIssue.hashCode() : 0);
        result = result * first + (dateOfPayment != null ? dateOfPayment.hashCode() : 0);
        result = result * first + (status != null ? status.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder("Bill{")
                .append("id=")
                .append(id)
                .append(", amount=")
                .append(amount)
                .append(", dateOfIssue=")
                .append(", dateOfPayment=")
                .append(dateOfPayment)
                .append(", status=")
                .append(status)
                .append('}')
                .toString();
    }
}