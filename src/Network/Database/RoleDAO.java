package Network.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RoleDAO extends DAO {
    public RoleDAO() throws SQLException {
        super();
    }

    public HashMap<Integer, String> getAllRoles() throws SQLException
    {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM role");
            ResultSet results = statement.executeQuery();
            HashMap<Integer, String> roles = new HashMap<Integer, String>();

            while(results.next()) {
                roles.put(results.getInt("role_id"), results.getString("role_name"));
            }
            return roles;
        }
    }
}
