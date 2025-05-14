package Model;

import java.io.Serializable;
import java.sql.SQLException;

public class Employee implements Serializable {
    private int employee_id;
    private Role role;
    private String username;
    private String status;

    public Employee(int employee_id, int role_id, String username) throws SQLException {
        this.employee_id = employee_id;
        this.role = RoleFactory.getInstance().getRole(role_id);
        this.username = username;
        this.status = "active";
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

    public String getStatus() {
        return status;
    }

    public void deativate() {
        this.status = "inactive";
    }

    public void activate()
    {
        this.status = "active";
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
