package repository;

import client.CompanyDAO;
import model.Company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAORepository implements CompanyDAO {

    private final Database database;

    public CompanyDAORepository(Database database) {
        this.database = database;
    }

    public List<Company> getAllCompanies() {
        String query = Query.SELECT_ALL.formatted("COMPANY");
        List<Company> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(database.getUrl());
             Statement statement = connection.createStatement();
             ResultSet sqlResults = statement.executeQuery(query);
        ) {
            while (sqlResults.next()) {
                int id = sqlResults.getInt("ID");
                String name = sqlResults.getString("NAME");
                Company company = new Company(id, name);
                result.add(company);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Company getCompany(int id) {
        String query = Query.GET_COMPANY.formatted(id);
        try (Connection connection = DriverManager.getConnection(database.getUrl());
             Statement statement = connection.createStatement();
             ResultSet sqlResults = statement.executeQuery(query);
        ) {
            while (sqlResults.next()) {
                String name = sqlResults.getString("NAME");
                return new Company(id, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addCompany(String companyName) {

        String query = Query.INSERT_COMPANY.formatted(companyName);
        database.executeQuery(query);
    }
}
