package carsharing.repository;

public class Query {

    final static String CREATE_COMPANY_TABLE = """
            CREATE TABLE IF NOT EXISTS COMPANY(
            ID INTEGER AUTO_INCREMENT PRIMARY KEY,
            NAME VARCHAR(255) NOT NULL UNIQUE);
            """;

    final static String CREATE_CAR_TABLE = """
            CREATE TABLE IF NOT EXISTS CAR(
            ID INTEGER AUTO_INCREMENT PRIMARY KEY,
            NAME VARCHAR(255) NOT NULL UNIQUE,
            COMPANY_ID INTEGER NOT NULL,
            CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID)
            REFERENCES COMPANY(ID)
            ON DELETE SET NULL
            ON UPDATE CASCADE);
            """;

    final static String CREATE_CUSTOMER_TABLE = """
            CREATE TABLE IF NOT EXISTS CUSTOMER(
            ID INTEGER AUTO_INCREMENT PRIMARY KEY,
            NAME VARCHAR(255) NOT NULL UNIQUE,
            RENTED_CAR_ID INTEGER,
            CONSTRAINT FK_CAR FOREIGN KEY (RENTED_CAR_ID)
            REFERENCES CAR(ID)
            ON DELETE SET NULL
            ON UPDATE CASCADE);
            """;

    final static String SELECT_ALL = """
            SELECT * FROM %s;
            """;

    final static String GET_COMPANY = """
            SELECT * FROM COMPANY
            WHERE ID=%d;
            """;

    final static String INSERT_COMPANY = """
            INSERT INTO COMPANY (NAME)
            VALUES ('%s');
            """;

    final static String INSERT_CAR = """
            INSERT INTO CAR (NAME, COMPANY_ID)
            VALUES ('%s', %d);
            """;

    final static String GET_COMPANY_CARS = """
            SELECT * FROM CAR
            WHERE COMPANY_ID=%d;
            """;

    final static String GET_AVAILABLE_COMPANY_CARS = """
            SELECT CAR.* FROM CAR
            LEFT JOIN CUSTOMER ON CAR.ID = CUSTOMER.RENTED_CAR_ID
            WHERE CAR.COMPANY_ID=%d
            AND CUSTOMER.RENTED_CAR_ID IS NULL;
            """;

    final static String GET_CAR = """
            SELECT * FROM CAR
            WHERE ID=%d;
            """;

    final static String INSERT_CUSTOMER = """
            INSERT INTO CUSTOMER (NAME)
            VALUES ('%s');
            """;

    final static String GET_CUSTOMER = """
            SELECT * FROM CUSTOMER
            WHERE ID=%d;
            """;

    final static String RETURN_CAR = """
            UPDATE CUSTOMER
            SET RENTED_CAR_ID = NULL
            WHERE ID=%d;
            """;

    final static String RENT_CAR = """
            UPDATE CUSTOMER
            SET RENTED_CAR_ID = %d
            WHERE ID=%d;
            """;
}
