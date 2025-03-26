package client;

import model.Car;

import java.util.List;

public interface CarDAO {

    void addCarToCompany(String carName, int companyId);

    List<Car> getCompanyCars(int companyId);

    List<Car> getAvailableCompanyCars(int companyId);

    Car getCar(int id);
}
