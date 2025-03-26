package client;

import model.Customer;

import java.util.List;

public interface CustomerDAO {

    List<Customer> getAllCustomers();

    Customer getCustomer(int id);

    void addCustomer(String customerName);

    void returnCar(int id);

    void rentCar(int customerId, int carId);

}
