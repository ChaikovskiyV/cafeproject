package by.vchaikovski.coffeshop.entity;

import java.util.concurrent.atomic.AtomicLong;

public class AddressDelivery {
    private static AtomicLong atomicId = new AtomicLong(0);
    private long id;
    private String streetName;
    private int houseNumber;
    private int buildingNumber;
    private int flatNumber;

    {
        id = atomicId.incrementAndGet();
    }

    public AddressDelivery(String streetName, int houseNumber, int buildingNumber, int flatNumber) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
    }

    public AddressDelivery(String streetName, int houseNumber, int flatNumber) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
    }

    public AddressDelivery(String streetName, int houseNumber) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
    }

    public long getId() {
        return id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
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
        return id == address.id && houseNumber == address.houseNumber &&
                buildingNumber == address.buildingNumber && flatNumber == address.flatNumber &&
                streetName != null ? streetName.equals(address.streetName) : address.streetName == null;
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first * (int) id;
        result = result * first + (streetName != null ? streetName.hashCode() : 0);
        result = result * first * houseNumber;
        result = result * first + buildingNumber;
        result = result * first + flatNumber;

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder("AddressDelivery{")
                .append("id=")
                .append(id)
                .append(", streetName='")
                .append(streetName)
                .append(", houseNumber=")
                .append(houseNumber)
                .append(", buildingNumber=")
                .append(buildingNumber)
                .append(", flatNumber=")
                .append(flatNumber)
                .append('}')
                .toString();
    }
}