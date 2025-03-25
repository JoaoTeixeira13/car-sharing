package carsharing.model;

public class Car {
    private final int id;
    private final int companyId;
    private final String name;

    public Car(int id, int companyId, String name) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

}
