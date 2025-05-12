package Model;

import Network.Database.RoleDAO;

import java.sql.SQLException;
import java.util.HashMap;

public class RoleFactory {
    private static RoleFactory instance;
    private HashMap<Integer, String> roles;

    private RoleFactory() throws SQLException {
        roles = new HashMap<>();
        try{
            RoleDAO roleDAO = new RoleDAO();
            roles = roleDAO.getAllRoles();
        } catch (SQLException e) {
            System.out.println("Couldn't successfuly get get roles from the database");
        }
    }

    public static RoleFactory getInstance() throws SQLException {
        if (instance == null) {
            instance = new RoleFactory();
        }
        return instance;
    }

    public Role getRole(int role_id) {
        String roleName = roles.get(role_id);
        if (roleName == null) {
            return null;
        }
        return new Role(role_id, roleName);
    }
}
