package by.vchaikovski.coffeeshop.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BankCard extends AbstractEntity {
    private String cardNumber;
    private LocalDate expirationDate;
    private BigDecimal amount;

    public BankCard(BankCardBuilder builder) {
        if (builder == null || !builder.isValid()) {
            String message = "The builder " + builder + " is not valid.";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        super.setId(builder.id);
        cardNumber = builder.cardNumber;
        expirationDate = builder.expirationDate;
        amount = builder.amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount != null) {
            this.amount = amount;
        }
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankCard bankCard = (BankCard) o;
        return super.equals(bankCard) && (amount != null ? amount.equals(bankCard.amount) : bankCard.amount == null) &&
                (expirationDate != null ? expirationDate.equals(bankCard.expirationDate) : bankCard.expirationDate == null) &&
                (cardNumber != null ? cardNumber.equals(bankCard.cardNumber) : bankCard.cardNumber == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + super.hashCode();
        result = result * first + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = result * first + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = result * first + (amount != null ? amount.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer(super.toString())
                .append(", cardNumber = ")
                .append(cardNumber)
                .append(", expirationDate = ")
                .append(expirationDate)
                .append(", amount = ")
                .append(amount)
                .append('}')
                .toString();
    }

    public static class BankCardBuilder {
        private long id;
        private String cardNumber;
        private LocalDate expirationDate;
        private BigDecimal amount;

        public BankCardBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public BankCardBuilder setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public BankCardBuilder setExpirationDate(LocalDate expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public BankCardBuilder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public boolean isValid() {
            return cardNumber != null && expirationDate != null && amount != null;
        }

        public BankCard build() {
            return new BankCard(this);
        }
    }
}