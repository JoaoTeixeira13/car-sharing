package repository;

import client.CustomerDAO;
import model.Customer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static repository.Query.GET_ALL_CUSTOMERS;
import static repository.Query.GET_CUSTOMER;
import static repository.Query.INSERT_CUSTOMER;
import static repository.Query.RETURN_CAR;
import static repository.Query.RENT_CAR;

import static repository.Util.getResultSet;


public class CustomerDAORepository implements CustomerDAO {

    private static final Logger LOGGER = Logger.getLogger(CustomerDAO.class.getName());


    private final Database database;

    public CustomerDAORepository(Database database) {
        this.database = database;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> result = new ArrayList<>();
        try (ResultSet sqlResults = getResultSet(GET_ALL_CUSTOMERS, database.getUrl());) {
            while (sqlResults.next()) {
                int id = sqlResults.getInt("ID");
                String name = sqlResults.getString("NAME");
                int rentedCarId = sqlResults.getInt("RENTED_CAR_ID");
                Customer customer = new Customer(id, rentedCarId, name);
                result.add(customer);
            }

            return result;
        } catch (Exception e) {
            LOGGER.severe("Error fetching all customers: %s".formatted(e.getMessage()));
        }
        return result;
    }

    public Customer getCustomer(int id) {
        try (ResultSet sqlResults = getResultSet(GET_CUSTOMER.formatted(id), database.getUrl());) {
            while (sqlResults.next()) {
                String name = sqlResults.getString("NAME");
                int rentedCarId = sqlResults.getInt("RENTED_CAR_ID");

                return new Customer(id, rentedCarId, name);
            }
        } catch (Exception e) {
            LOGGER.severe("Error fetching customer with id '%d': %s".formatted(id, e.getMessage()));
        }
        return null;
    }

    public void addCustomer(String customerName) {
        database.executeQuery(INSERT_CUSTOMER.formatted(customerName));
    }

    public void returnCar(int id) {
        database.executeQuery(RETURN_CAR.formatted(id));
    }

    public void rentCar(int customerId, int rentedCarId) {
        database.executeQuery(RENT_CAR.formatted(rentedCarId, customerId));
    }
}
