package Model;

public class Employee {
    private int employee_id;
    private Role role;
    private String username;
    private String password;

    public Employee(int employee_id, int role_id, String username, String password) {
        this.employee_id = employee_id;
        this.role = new Role(role_id);
        this.username = username;
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setPassword(String password) {
        this.password = password;
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
