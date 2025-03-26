package client;

import model.Company;
import repository.Database;

import java.util.List;

public interface CompanyDAO {

    List<Company> getAllCompanies(Database database);

    Company getCompany(int id, Database database);

    void addCompany(String companyName, Database database);
}

