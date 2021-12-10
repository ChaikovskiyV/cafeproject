package by.vchaikovski.coffeshop.entity;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public class BankCard {
    private static AtomicLong atomicId = new AtomicLong(0);
    private long id;
    private String cardNumber;
    private BigDecimal amount;

    {
        id = atomicId.incrementAndGet();
    }

    public BankCard(String cardNumber, BigDecimal amount) {
        this.cardNumber = cardNumber;
        this.amount = amount != null ? amount : new BigDecimal(0);
    }

    public BankCard(String cardNumber) {
        this.cardNumber = cardNumber;
        amount = new BigDecimal(0);
    }

    public long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount != null) {
            this.amount = amount;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankCard bankCard = (BankCard) o;
        return id == bankCard.id && (amount != null ? amount.equals(bankCard.amount) : bankCard.amount == null) &&
                (cardNumber != null ? cardNumber.equals(bankCard.cardNumber) : bankCard.cardNumber == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first * (int) id;
        result = result * first + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = result * first + (amount != null ? amount.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder("BankCard{")
                .append("id=")
                .append(id)
                .append(", cardNumber='")
                .append(cardNumber)
                .append(", amount=")
                .append(amount)
                .append('}')
                .toString();
    }
}