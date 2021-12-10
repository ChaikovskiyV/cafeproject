package by.vchaikovski.coffeshop.entity;

import java.util.concurrent.atomic.AtomicLong;

public class Discount {
    public enum DiscountType {ZERO, FOR_STAFF, PERSONAL}

    private static AtomicLong atomicId = new AtomicLong(0);
    private long id;
    private DiscountType type;
    private int rate;

    public Discount(DiscountType type, int rate) {
        id = atomicId.incrementAndGet();
        this.type = type;
        this.rate = (type == DiscountType.ZERO) ? 0 : rate;
    }

    public Discount(DiscountType type) {
        id = atomicId.incrementAndGet();
        this.type = type;
        rate = 0;
    }

    public long getId() {
        return id;
    }

    public DiscountType getType() {
        return type;
    }

    public void setType(DiscountType type) {
        this.type = type;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = (type == DiscountType.ZERO) ? 0 : rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return id == discount.id && rate == discount.rate && type == discount.type;
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first * (int) id;
        result = result * first + (type != null ? type.hashCode() : 0);
        result = result * first + rate;

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder("Discount{")
                .append("id=")
                .append(id)
                .append(", type=")
                .append(type)
                .append(", rate=")
                .append(rate)
                .append('}')
                .toString();
    }
}