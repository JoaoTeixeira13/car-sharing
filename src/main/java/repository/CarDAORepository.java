package carsharing.repository;

import carsharing.client.CarDAO;
import carsharing.model.Car;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDAORepository implements CarDAO {

    public List<Car> getCompanyCars(int companyId, Database database) {
        String query = Query.GET_COMPANY_CARS.formatted(companyId);
        List<Car> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(database.getUrl());
             Statement statement = connection.createStatement();
             ResultSet sqlResults = statement.executeQuery(query);
        ) {
            while (sqlResults.next()) {
                int id = sqlResults.getInt("ID");
                String name = sqlResults.getString("NAME");
                Car car = new Car(id, companyId, name);
                result.add(car);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Car> getAvailableCompanyCars(int companyId, Database database) {
        String query = Query.GET_AVAILABLE_COMPANY_CARS.formatted(companyId);
        List<Car> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(database.getUrl());
             Statement statement = connection.createStatement();
             ResultSet sqlResults = statement.executeQuery(query);
        ) {
            while (sqlResults.next()) {
                int id = sqlResults.getInt("ID");
                String name = sqlResults.getString("NAME");
                Car car = new Car(id, companyId, name);
                result.add(car);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Car getCar(int id, Database database) {
        String query = Query.GET_CAR.formatted(id);
        try (Connection connection = DriverManager.getConnection(database.getUrl());
             Statement statement = connection.createStatement();
             ResultSet sqlResults = statement.executeQuery(query);
        ) {
            while (sqlResults.next()) {
                int companyId = sqlResults.getInt("COMPANY_ID");
                String name = sqlResults.getString("NAME");
                return new Car(id, companyId, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addCarToCompany(String carName, int companyId, Database database) {
        String query = Query.INSERT_CAR.formatted(carName, companyId);
        database.executeQuery(query);
    }
}
