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

public class MenuService {
    private final Database database;
    private final Scanner scanner;
    private final CompanyDAORepository companyDAORepository;
    private final CarDAORepository carDAORepository;
    private final CustomerDAORepository customerDAORepository;

    public MenuService(Database database) {
        this.database = database;
        this.scanner = new Scanner(System.in);
        this.companyDAORepository = new CompanyDAORepository(database);
        this.carDAORepository = new CarDAORepository(database);
        this.customerDAORepository = new CustomerDAORepository();
    }

    public void start() {

        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");

            switch (scanner.nextLine().trim()) {
                case "" -> System.out.println("No input.");
                case "1" -> logInAsManager();
                case "2" -> logInAsCustomer();
                case "3" -> createCustomer();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Unknown command!");
            }
        }

    }

    private void logInAsManager() {
        while (true) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");

            switch (scanner.nextLine().trim()) {
                case "" -> System.out.println("No input.");
                case "1" -> displayCompanies();
                case "2" -> createCompany();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Unknown command!");
            }

        }
    }

    private void logInAsCustomer() {

        List<Customer> customers = customerDAORepository.getAllCustomers(database);

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

    private void createCustomer() {
        System.out.println("Enter the customer name:");
        String customerName = scanner.nextLine().trim();
        customerDAORepository.addCustomer(customerName, database);
        System.out.println("The customer was added!\n");
    }

    private void displayCustomer(int customerId) {

        Customer customer = customerDAORepository.getCustomer(customerId, database);

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
        customerDAORepository.rentCar(customer.getId(), rentedCar.getId(), database);
        customer.setRentedCarId(rentedCar.getId());
        System.out.printf("You rented '%s'%n", rentedCar.getName());
    }


    private void returnCarRental(Customer customer) {
        if (customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
            return;
        }
        customerDAORepository.returnCar(customer.getId(), database);
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

    private void displayCompanies() {
        List<Company> companies = companyDAORepository.getAllCompanies();


        if (companies.isEmpty()) {
            System.out.println("The company list is empty!\n");
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
        displayCompany(companies.get(companyDisplayId - 1).getId());


    }

    private void displayCompany(int companyId) {

        Company company = companyDAORepository.getCompany(companyId);

        if (company == null) {
            System.out.printf("No company found for companyID %d%n", companyId);
            return;
        }

        System.out.printf("'%s' company%n", company.getName());

        while (true) {

            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");

            int optionInput = validateIntegerInput(scanner.nextLine().trim());

            switch (optionInput) {
                case 0 -> {
                    return;
                }
                case 1 -> displayCompanyCars(companyId);
                case 2 -> createCompanyCar(companyId);
            }
        }
    }

    private void createCompany() {
        System.out.println("Enter the company name:");
        String companyName = scanner.nextLine().trim();
        companyDAORepository.addCompany(companyName);
        System.out.println("The company was created!\n");
    }

    private void displayCompanyCars(int companyId) {
        List<Car> companyCars = carDAORepository.getCompanyCars(companyId);

        if (companyCars.isEmpty()) {
            System.out.println("The car list is empty!\n");

            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");

            int optionInput = validateIntegerInput(scanner.nextLine().trim());

            switch (optionInput) {
                case 0 -> {
                    return;
                }
                case 1 -> displayCompanyCars(companyId);
                case 2 -> createCompanyCar(companyId);
            }

        } else {
            System.out.println("Car list:");
            for (Car car : companyCars) {
                System.out.printf("%d. %s%n", companyCars.indexOf(car) + 1, car.getName());
            }
        }

    }

    private void createCompanyCar(int companyId) {
        System.out.println("Enter the car name:");
        String carName = scanner.nextLine().trim();

        carDAORepository.addCarToCompany(carName, companyId);
        System.out.println("The car was added!");
    }

    private int validateIntegerInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
