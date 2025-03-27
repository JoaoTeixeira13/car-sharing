package application;

import model.Car;
import model.Company;
import model.Customer;
import repository.CarDAORepository;
import repository.CompanyDAORepository;
import repository.CustomerDAORepository;
import repository.Database;

import java.util.List;
import java.util.Scanner;

import static application.Util.validateIntegerInput;

public class CustomerMenuService {

    private final Scanner scanner;
    private final CustomerDAORepository customerDAORepository;
    private final CompanyDAORepository companyDAORepository;
    private final CarDAORepository carDAORepository;

    public CustomerMenuService(Database database, Scanner scanner) {
        this.scanner = scanner;
        this.customerDAORepository = new CustomerDAORepository(database);
        this.companyDAORepository = new CompanyDAORepository(database);
        this.carDAORepository = new CarDAORepository(database);
    }

    public void logInAsCustomer() {
        List<Customer> customers = customerDAORepository.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            return;
        }
        System.out.println("Choose a customer:");
        for (Customer customer : customers) {
            System.out.printf("%s. %s%n", customers.indexOf(customer) + 1, customer.getName());
        }
        System.out.println("0. Back");
        int customerDisplayId = validateIntegerInput(scanner.nextLine().trim());
        if (customerDisplayId == 0) {
            return;
        }
        displayCustomer(customers.get(customerDisplayId - 1).getId());
    }

    public void createCustomer() {
        System.out.println("Enter the customer name:");
        String customerName = scanner.nextLine().trim();
        customerDAORepository.addCustomer(customerName);
        System.out.println("The customer was added!\n");
    }

    private void displayCustomer(int customerId) {
        Customer customer = customerDAORepository.getCustomer(customerId);
        if (customer == null) {
            System.out.printf("No customer found for customerID %d%n", customerId);
            return;
        }
        while (true) {
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");
            int optionInput = validateIntegerInput(scanner.nextLine().trim());

            switch (optionInput) {
                case 0 -> {
                    return;
                }
                case 1 -> rentCar(customer);
                case 2 -> returnCarRental(customer);
                case 3 -> displayRentalInfo(customer.getRentedCarId());
            }
        }
    }

    private void rentCar(Customer customer) {
        List<Company> companies = companyDAORepository.getAllCompanies();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }
        if (customer.getRentedCarId() != 0) {
            System.out.println("You've already rented a car!");
            return;
        }

        System.out.println("Choose the company:");
        for (Company company : companies) {
            System.out.printf("%s. %s%n", companies.indexOf(company) + 1, company.getName());
        }
        System.out.println("0. Back");
        int companyDisplayId = validateIntegerInput(scanner.nextLine().trim());
        if (companyDisplayId == 0) {
            return;
        }
        displayCompanyRentals(companies.get(companyDisplayId - 1), customer);
    }

    public void displayCompanyRentals(Company company, Customer customer) {
        List<Car> availableCompanyCars = carDAORepository.getAvailableCompanyCars(company.getId());
        if (availableCompanyCars.isEmpty()) {
            System.out.printf("No available cars in the '%s' company. %n", company.getName());
            return;
        }
        System.out.println("Choose a car:");
        for (Car car : availableCompanyCars) {
            System.out.printf("%s. %s%n", availableCompanyCars.indexOf(car) + 1, car.getName());
        }
        System.out.println("0. Back");
        int carDisplayId = validateIntegerInput(scanner.nextLine().trim());
        if (carDisplayId == 0) {
            return;
        }
        rentCarToCustomer(customer, availableCompanyCars.get(carDisplayId - 1));
    }

    public void rentCarToCustomer(Customer customer, Car rentedCar) {
        customerDAORepository.rentCar(customer.getId(), rentedCar.getId());
        customer.setRentedCarId(rentedCar.getId());
        System.out.printf("You rented '%s'%n", rentedCar.getName());
    }

    private void returnCarRental(Customer customer) {
        if (customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
            return;
        }
        customerDAORepository.returnCar(customer.getId());
        customer.setRentedCarId(0);
        System.out.println("You've returned a rented car!");
    }

    private void displayRentalInfo(int rentedCarId) {
        if (rentedCarId == 0) {
            System.out.println("You didn't rent a car!");
            return;
        }
        Car carRental = carDAORepository.getCar(rentedCarId);
        if (carRental == null) {
            return;
        }
        Company company = companyDAORepository.getCompany(carRental.getCompanyId());
        if (company == null) {
            return;
        }
        System.out.println("Your rented car:");
        System.out.println(carRental.getName());
        System.out.println("Company:");
        System.out.println(company.getName());
    }
}
