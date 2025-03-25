package carsharing.client;

import carsharing.model.Company;
import carsharing.repository.Database;

import java.util.List;

public interface CompanyDAO {

    List<Company> getAllCompanies(Database database);

    Company getCompany(int id, Database database);

    void addCompany(String companyName, Database database);
}

