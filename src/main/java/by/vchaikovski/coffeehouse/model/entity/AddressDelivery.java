package by.vchaikovski.coffeehouse.model.entity;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Address delivery.
 */
public class AddressDelivery extends AbstractEntity {
    private String streetName;
    private String houseNumber;
    private int buildingNumber;
    private int flatNumber;

    private AddressDelivery(AddressDeliveryBuilder builder) {
        super.setId(builder.id);
        streetName = builder.streetName;
        houseNumber = builder.houseNumber;
        buildingNumber = builder.buildingNumber;
        flatNumber = builder.flatNumber;
    }

    /**
     * Gets street name.
     *
     * @return the street name
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets street name.
     *
     * @param streetName the street name
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Gets house number.
     *
     * @return the house number
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Sets house number.
     *
     * @param houseNumber the house number
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Gets building number.
     *
     * @return the building number
     */
    public int getBuildingNumber() {
        return buildingNumber;
    }

    /**
     * Sets building number.
     *
     * @param buildingNumber the building number
     */
    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    /**
     * Gets flat number.
     *
     * @return the flat number
     */
    public int getFlatNumber() {
        return flatNumber;
    }

    /**
     * Sets flat number.
     *
     * @param flatNumber the flat number
     */
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

    /**
     * The type Address delivery builder.
     */
    public static class AddressDeliveryBuilder {
        private long id;
        private String streetName;
        private String houseNumber;
        private int buildingNumber;
        private int flatNumber;

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public AddressDeliveryBuilder setId(long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets street name.
         *
         * @param streetName the street name
         * @return the street name
         */
        public AddressDeliveryBuilder setStreetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        /**
         * Sets house number.
         *
         * @param houseNumber the house number
         * @return the house number
         */
        public AddressDeliveryBuilder setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        /**
         * Sets building number.
         *
         * @param buildingNumber the building number
         * @return the building number
         */
        public AddressDeliveryBuilder setBuildingNumber(int buildingNumber) {
            this.buildingNumber = buildingNumber;
            return this;
        }

        /**
         * Sets flat number.
         *
         * @param flatNumber the flat number
         * @return the flat number
         */
        public AddressDeliveryBuilder setFlatNumber(int flatNumber) {
            this.flatNumber = flatNumber;
            return this;
        }

        /**
         * Build address delivery.
         *
         * @return the address delivery
         */
        public AddressDelivery build() {
            return new AddressDelivery(this);
        }
    }
}