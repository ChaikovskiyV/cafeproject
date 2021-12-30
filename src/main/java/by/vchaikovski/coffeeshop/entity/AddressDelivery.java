package by.vchaikovski.coffeeshop.entity;

public class AddressDelivery extends AbstractEntity {
    private String streetName;
    private String houseNumber;
    private int buildingNumber;
    private int flatNumber;

    private AddressDelivery(AddressDeliveryBuilder builder) {
        if (builder == null || !builder.isValid()) {
            String message = "The builder " + builder + " is not valid.";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        super.setId(builder.id);
        streetName = builder.streetName;
        houseNumber = builder.houseNumber;
        buildingNumber = builder.buildingNumber;
        flatNumber = builder.flatNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public int getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(int flatNumber) {
        this.flatNumber = flatNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDelivery address = (AddressDelivery) o;
        return super.equals(address) && buildingNumber == address.buildingNumber && flatNumber == address.flatNumber &&
                (houseNumber != null ? houseNumber.equals(address.houseNumber) : address.houseNumber == null) &&
                (streetName != null ? streetName.equals(address.streetName) : address.streetName == null);
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + super.hashCode();
        result = result * first + (streetName != null ? streetName.hashCode() : 0);
        result = result * first + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = result * first + buildingNumber;
        result = result * first + flatNumber;

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer(super.toString())
                .append(", streetName = ")
                .append(streetName)
                .append(", houseNumber = ")
                .append(houseNumber)
                .append(", buildingNumber = ")
                .append(buildingNumber)
                .append(", flatNumber = ")
                .append(flatNumber)
                .append('}')
                .toString();
    }

    public static class AddressDeliveryBuilder {
        private long id;
        private String streetName;
        private String houseNumber;
        private int buildingNumber;
        private int flatNumber;

        public AddressDeliveryBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public AddressDeliveryBuilder setStreetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public AddressDeliveryBuilder setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public AddressDeliveryBuilder setBuildingNumber(int buildingNumber) {
            this.buildingNumber = buildingNumber;
            return this;
        }

        public AddressDeliveryBuilder setFlatNumber(int flatNumber) {
            this.flatNumber = flatNumber;
            return this;
        }

        public boolean isValid() {
            return streetName != null && houseNumber != null;
        }

        public AddressDelivery build() {
            return new AddressDelivery(this);
        }
    }
}