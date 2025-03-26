package client;

import model.Company;

import java.util.List;

public interface CompanyDAO {

    List<Company> getAllCompanies();

    Company getCompany(int id);

    void addCompany(String companyName);
}

