package carsharing.client;

import carsharing.model.Customer;
import carsharing.repository.Database;

import java.util.List;

public interface CustomerDAO {

    List<Customer> getAllCustomers(Database database);

    Customer getCustomer(int id, Database database);

    void addCustomer(String customerName, Database database);

    void returnCar(int id, Database database);

    void rentCar(int customerId, int carId, Database database);

}
