package repository;

import client.CustomerDAO;
import model.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAORepository implements CustomerDAO {

    private final Database database;

    public CustomerDAORepository(Database database) {
        this.database = database;
    }

    public List<Customer> getAllCustomers() {
        String query = Query.SELECT_ALL.formatted("CUSTOMER");
        List<Customer> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(database.getUrl());
             Statement statement = connection.createStatement();
             ResultSet sqlResults = statement.executeQuery(query);
        ) {
            while (sqlResults.next()) {
                int id = sqlResults.getInt("ID");
                String name = sqlResults.getString("NAME");
                int rentedCarId = sqlResults.getInt("RENTED_CAR_ID");
                Customer customer = new Customer(id, rentedCarId, name);
                result.add(customer);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Customer getCustomer(int id) {
        String query = Query.GET_CUSTOMER.formatted(id);
        try (Connection connection = DriverManager.getConnection(database.getUrl());
             Statement statement = connection.createStatement();
             ResultSet sqlResults = statement.executeQuery(query);
        ) {
            while (sqlResults.next()) {
                String name = sqlResults.getString("NAME");
                int rentedCarId = sqlResults.getInt("RENTED_CAR_ID");

                return new Customer(id, rentedCarId, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addCustomer(String customerName) {

        String query = Query.INSERT_CUSTOMER.formatted(customerName);
        database.executeQuery(query);
    }

    public void returnCar(int id) {
        String query = Query.RETURN_CAR.formatted(id);
        database.executeQuery(query);
    }

    public void rentCar(int customerId, int rentedCarId) {
        String query = Query.RENT_CAR.formatted(rentedCarId, customerId);
        database.executeQuery(query);
    }
}
