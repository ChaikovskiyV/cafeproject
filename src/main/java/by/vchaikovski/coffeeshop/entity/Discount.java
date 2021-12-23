package by.vchaikovski.coffeeshop.entity;

public class Discount extends AbstractEntity {
    public enum DiscountType {ZERO, STAFF, PERSONAL}

    private long id;
    private DiscountType type;
    private int rate;

    public Discount(DiscountBuilder builder) {
        id = builder.id;
        type = builder.type;
        rate = builder.rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return id == discount.id && rate == discount.rate && type == discount.type;
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + (int) id;
        result = result * first + (type != null ? type.hashCode() : 0);
        result = result * first + rate;

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer("Discount{")
                .append("id=")
                .append(id)
                .append(", type=")
                .append(type)
                .append(", rate=")
                .append(rate)
                .append('}')
                .toString();
    }

    public static class DiscountBuilder {
        private long id;
        private DiscountType type;
        private int rate;

        public DiscountBuilder() {
        }

        public DiscountBuilder(long id, DiscountType type, int rate) {
            this.id = id;
            this.type = type;
            this.rate = rate;
        }

        public DiscountBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public DiscountBuilder setType(DiscountType type) {
            this.type = type;
            return this;
        }

        public DiscountBuilder setRate(int rate) {
            this.rate = rate;
            return this;
        }

        public boolean isValid() {
            return type != null;
        }

        public Discount build() {
            return new Discount(this);
        }
    }
}