package client;

import model.Car;
import repository.Database;

import java.util.List;

public interface CarDAO {

    void addCarToCompany(String carName, int companyId, Database database);

    List<Car> getCompanyCars(int companyId, Database database);

    List<Car> getAvailableCompanyCars(int companyId, Database database);

    Car getCar(int id, Database database);
}
