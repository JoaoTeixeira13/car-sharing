package carsharing.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {

    private final String url;

    public Database(String[] args) {
        try {
            Class.forName("org.h2.Driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String name = args.length == 2 ? args[1] : "database";
        url = "jdbc:h2:./src/carsharing/db/" + name;

        executeQuery(Query.CREATE_COMPANY_TABLE);
        executeQuery(Query.CREATE_CAR_TABLE);
        executeQuery(Query.CREATE_CUSTOMER_TABLE);
    }

    public void executeQuery(String query) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(true);

            statement.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }
}
