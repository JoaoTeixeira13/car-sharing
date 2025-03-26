package repository;

import client.CarDAO;
import model.Car;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static repository.Query.GET_COMPANY_CARS;
import static repository.Query.GET_AVAILABLE_COMPANY_CARS;
import static repository.Query.GET_CAR;
import static repository.Query.INSERT_CAR;

import static repository.Util.getResultSet;


public class CarDAORepository implements CarDAO {

    private static final Logger LOGGER = Logger.getLogger(CarDAORepository.class.getName());

    private final Database database;

    public CarDAORepository(Database database) {
        this.database = database;
    }

    public List<Car> getCompanyCars(int companyId) {
        List<Car> result = new ArrayList<>();
        try (ResultSet sqlResults = getResultSet(GET_COMPANY_CARS.formatted(companyId), database.getUrl())) {
            while (sqlResults.next()) {
                int id = sqlResults.getInt("ID");
                String name = sqlResults.getString("NAME");
                Car car = new Car(id, companyId, name);
                result.add(car);
            }

            return result;
        } catch (Exception e) {
            LOGGER.severe("Error fetching cars for company with id '%d': %s".formatted(companyId, e.getMessage()));
        }
        return result;
    }

    public List<Car> getAvailableCompanyCars(int companyId) {
        List<Car> result = new ArrayList<>();
        try (ResultSet sqlResults = getResultSet(GET_AVAILABLE_COMPANY_CARS.formatted(companyId), database.getUrl())) {
            while (sqlResults.next()) {
                int id = sqlResults.getInt("ID");
                String name = sqlResults.getString("NAME");
                Car car = new Car(id, companyId, name);
                result.add(car);
            }

            return result;
        } catch (Exception e) {
            LOGGER.severe("Error fetching available cars for company with id '%d': %s".formatted(companyId, e.getMessage()));
        }
        return result;
    }

    public Car getCar(int id) {
        try (ResultSet sqlResults = getResultSet(GET_CAR.formatted(id), database.getUrl())) {
            while (sqlResults.next()) {
                int companyId = sqlResults.getInt("COMPANY_ID");
                String name = sqlResults.getString("NAME");
                return new Car(id, companyId, name);
            }
        } catch (Exception e) {
            LOGGER.severe("Error car with id '%d': %s".formatted(id, e.getMessage()));
        }
        return null;
    }

    public void addCarToCompany(String carName, int companyId) {
        database.executeQuery(INSERT_CAR.formatted(carName, companyId));
    }
}
