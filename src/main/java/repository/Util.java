package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Util {

     static ResultSet getResultSet(String query, String url) throws Exception {
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
}
