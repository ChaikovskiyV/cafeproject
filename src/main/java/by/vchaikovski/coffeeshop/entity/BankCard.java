package by.vchaikovski.coffeeshop.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BankCard extends AbstractEntity {
    private static final Logger logger = LogManager.getLogger();
    private long id;
    private String cardNumber;
    private LocalDate expirationDate;
    private BigDecimal amount;

    public BankCard(BankCardBuilder builder) {
        if (builder == null || builder.isValid()) {
            logger.error(() -> "The builder " + builder + " is not valid.");
            throw new IllegalArgumentException("The builder " + builder + " is not valid.");
        }
        this.id = builder.id;
        this.cardNumber = builder.cardNumber;
        this.expirationDate = builder.expirationDate;
        this.amount = builder.amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return id == bankCard.id && (amount != null ? amount.equals(bankCard.amount) : bankCard.amount == null) &&
                (expirationDate != null ? expirationDate.equals(bankCard.expirationDate) : bankCard.expirationDate == null) &&
                (cardNumber != null ? cardNumber.equals(bankCard.cardNumber) : bankCard.cardNumber == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + (int) id;
        result = result * first + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = result * first + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = result * first + (amount != null ? amount.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer("BankCard{")
                .append("id=")
                .append(id)
                .append(", cardNumber=")
                .append(cardNumber)
                .append(", expirationDate=")
                .append(expirationDate)
                .append(", amount=")
                .append(amount)
                .append('}')
                .toString();
    }

    public static class BankCardBuilder {
        private long id;
        private String cardNumber;
        private LocalDate expirationDate;
        private BigDecimal amount;

        public BankCardBuilder() {
        }

        public BankCardBuilder(long id, String cardNumber, LocalDate expirationDate, BigDecimal amount) {
            this.id = id;
            this.cardNumber = cardNumber;
            this.expirationDate = expirationDate;
            this.amount = amount;
        }

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