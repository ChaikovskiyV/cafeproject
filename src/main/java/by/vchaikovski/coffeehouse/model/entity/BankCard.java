package by.vchaikovski.coffeehouse.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Bank card.
 */
public class BankCard extends AbstractEntity {
    private String cardNumber;
    private LocalDate expirationDate;
    private BigDecimal amount;

    /**
     * Instantiates a new Bank card.
     *
     * @param cardNumber     the card number
     * @param expirationDate the expiration date
     * @param amount         the amount
     */
    public BankCard(String cardNumber, LocalDate expirationDate, BigDecimal amount) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.amount = amount;
    }

    /**
     * Gets card number.
     *
     * @return the card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets card number.
     *
     * @param cardNumber the card number
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(BigDecimal amount) {
        if (amount != null) {
            this.amount = amount;
        }
    }

    /**
     * Gets expiration date.
     *
     * @return the expiration date
     */
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets expiration date.
     *
     * @param expirationDate the expiration date
     */
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
}