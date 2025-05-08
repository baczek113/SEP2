package Model;

public class Employee {
    private int employee_id;
    private int role_id;
    private String username;
    private String password;

    public Employee(int employee_id, int role_id, String username, String password) {
        this.employee_id = employee_id;
        this.role_id = role_id;
        this.username = username;
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
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

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
