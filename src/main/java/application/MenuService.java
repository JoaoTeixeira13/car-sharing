package application;

import repository.Database;

import java.util.List;
import java.util.Scanner;

import static application.Util.displayInfoText;
import static application.Util.validateIntegerInput;

public class MenuService {
    private final Scanner scanner;
    private final ManagerMenuService managerMenuService;
    private final CustomerMenuService customerMenuService;

    public MenuService(Database database) {
        this.scanner = new Scanner(System.in);
        this.managerMenuService = new ManagerMenuService(database, scanner);
        this.customerMenuService = new CustomerMenuService(database, scanner);
    }

    public void start() {

        while (true) {
            displayInfoText(List.of("1. Log in as a manager", "2. Log in as a customer", "3. Create a customer", "0. Exit"));

            switch (validateIntegerInput(scanner.nextLine().trim())) {
                case 1 -> managerMenuService.logInAsManager();
                case 2 -> customerMenuService.logInAsCustomer();
                case 3 -> customerMenuService.createCustomer();
                case 0 -> {
                    return;
                }
            }
        }
    }
}
