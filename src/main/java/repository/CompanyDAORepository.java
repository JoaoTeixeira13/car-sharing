package repository;

import client.CompanyDAO;
import model.Company;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static repository.Query.Column.ID;
import static repository.Query.Column.NAME;

import static repository.Query.GET_ALL_COMPANIES;
import static repository.Query.GET_COMPANY;
import static repository.Query.INSERT_COMPANY;

import static repository.Util.getResultSet;


public class CompanyDAORepository implements CompanyDAO {

    private static final Logger LOGGER = Logger.getLogger(CompanyDAORepository.class.getName());

    private final Database database;

    public CompanyDAORepository(Database database) {
        this.database = database;
    }

    public List<Company> getAllCompanies() {
        List<Company> result = new ArrayList<>();
        try (ResultSet sqlResults = getResultSet(GET_ALL_COMPANIES, database.getUrl())) {
            while (sqlResults.next()) {
                int id = sqlResults.getInt(ID);
                String name = sqlResults.getString(NAME);
                Company company = new Company(id, name);
                result.add(company);
            }
            return result;
        } catch (Exception e) {
            LOGGER.severe("Error fetching all companies: %s".formatted(e.getMessage()));
        }
        return result;
    }

    public Company getCompany(int id) {
        try (ResultSet sqlResults = getResultSet(GET_COMPANY.formatted(id), database.getUrl())) {
            while (sqlResults.next()) {
                String name = sqlResults.getString(NAME);
                return new Company(id, name);
            }
        } catch (Exception e) {
            LOGGER.severe("Error fetching company with id '%d': %s".formatted(id, e.getMessage()));
        }
        return null;
    }

    public void addCompany(String companyName) {
        database.executeQuery(INSERT_COMPANY.formatted(companyName));
    }
}
