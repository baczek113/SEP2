package Model;

import java.sql.SQLException;

public class Employee {
    private int employee_id;
    private Role role;
    private String username;

    public Employee(int employee_id, int role_id, String username) throws SQLException {
        this.employee_id = employee_id;
        this.role = RoleFactory.getInstance().getRole(role_id);
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public int getEmployee_id() {
        return employee_id;
    }
}
