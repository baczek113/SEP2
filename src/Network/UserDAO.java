package Network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO extends DAO{
    private static UserDAO instance;

    private UserDAO() throws SQLException {
        super();
    }

    public static UserDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public void addUser(int role_id, String username, String password) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO \"user\"(role_id, username, password) VALUES (?,?,?)");
            statement.setInt(1, role_id);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.executeUpdate();
        }
    }
}
