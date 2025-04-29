package Network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    protected DAO() throws SQLException
    {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    protected Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=sep_database", "postgres", "gigakoks1");
    }
}
