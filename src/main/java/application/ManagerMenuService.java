package application;

import model.Car;
import model.Company;
import repository.CarDAORepository;
import repository.CompanyDAORepository;
import repository.Database;

import java.util.List;
import java.util.Scanner;

import static application.Util.validateIntegerInput;

public class ManagerMenuService {

    private final Scanner scanner;
    private final CompanyDAORepository companyDAORepository;
    private final CarDAORepository carDAORepository;

    public ManagerMenuService(Database database, Scanner scanner) {
        this.scanner = scanner;
        this.companyDAORepository = new CompanyDAORepository(database);
        this.carDAORepository = new CarDAORepository(database);
    }

    public void logInAsManager() {
        while (true) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");

            switch (validateIntegerInput(scanner.nextLine().trim())) {
                case 1 -> displayCompanies();
                case 2 -> createCompany();
                case 0 -> {
                    return;
                }
            }
        }
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

    private void createCompany() {
        System.out.println("Enter the company name:");
        String companyName = scanner.nextLine().trim();
        companyDAORepository.addCompany(companyName);
        System.out.println("The company was created!\n");
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

    private void displayCompanyCars(int companyId) {
        List<Car> companyCars = carDAORepository.getCompanyCars(companyId);
        if (companyCars.isEmpty()) {
            System.out.println("The car list is empty!\n");
            return;
        }
        System.out.println("Car list:");
        for (Car car : companyCars) {
            System.out.printf("%d. %s%n", companyCars.indexOf(car) + 1, car.getName());
        }
    }

    private void createCompanyCar(int companyId) {
        System.out.println("Enter the car name:");
        String carName = scanner.nextLine().trim();
        carDAORepository.addCarToCompany(carName, companyId);
        System.out.println("The car was added!");
    }
}
