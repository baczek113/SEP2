package Model;

public class Employee {
    private int employee_id;
    private Role role;
    private String username;

    public Employee(int employee_id, int role_id, String username) {
        this.employee_id = employee_id;
        this.role = new Role(role_id);
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

    public void setRole(int role_id) {
        this.role = new Role(role_id);
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
