package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Logger;

import static repository.Query.CREATE_COMPANY_TABLE;
import static repository.Query.CREATE_CAR_TABLE;
import static repository.Query.CREATE_CUSTOMER_TABLE;


public class Database {

    private static final Logger LOGGER = Logger.getLogger(CompanyDAORepository.class.getName());

    private final String url;

    public Database(String[] args) {
        try {
            Class.forName("org.h2.Driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String name = args.length == 2 ? args[1] : "database";
        url = "jdbc:h2:./src/carsharing/db/" + name;
        executeQuery(CREATE_COMPANY_TABLE);
        executeQuery(CREATE_CAR_TABLE);
        executeQuery(CREATE_CUSTOMER_TABLE);
    }

    public void executeQuery(String query) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            statement.executeUpdate(query);
        } catch (Exception e) {
            LOGGER.severe("Error while executing query: %s".formatted(e.getMessage()));
        }
    }

    public String getUrl() {
        return url;
    }
}
