package model;

public class Customer {
    private final int id;
    private int rentedCarId;
    private final String name;

    public Customer(int id, int rentedCarId, String name) {
        this.id = id;
        this.rentedCarId = rentedCarId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(int rentedCarId) {
        this.rentedCarId = rentedCarId;
    }

    public String getName() {
        return name;
    }
}
