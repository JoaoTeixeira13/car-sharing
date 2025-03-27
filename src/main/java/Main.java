
import application.MenuService;
import repository.Database;


public class Main {

    public static void main(String[] args) {
        Database database = new Database(args);
        MenuService menuService = new MenuService(database);
        menuService.start();
    }
}