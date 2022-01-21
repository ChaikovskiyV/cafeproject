package by.vchaikovski.coffeeshop.model.entity;

public class Discount extends AbstractEntity {
    public enum DiscountType {ZERO, STAFF, PERSONAL}

    private DiscountType type;
    private int rate;

    public Discount(DiscountType type, int rate) {
        this.type = type != null ? type : DiscountType.ZERO;
        this.rate = rate;
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
        this.rate = Math.max(rate, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return super.equals(discount) && rate == discount.rate && type == discount.type;
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + super.hashCode();
        result = result * first + (type != null ? type.hashCode() : 0);
        result = result * first + rate;

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer(super.toString())
                .append(", type = ")
                .append(type)
                .append(", rate = ")
                .append(rate)
                .append('}')
                .toString();
    }
}