package Model;

public class Employee {
  private int user_id;
  private int role_id;
  private String username;
  private String password;

  public Employee(int user_id, int role_id, String username, String password) {
    this.user_id = user_id;
    this.role_id = role_id;
    this.username = username;
    this.password = password;
  }

  public Employee() {
  }

  public int getUser_id() {
    return user_id;
  }

  public int getRole_id() {
    return role_id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public void setRole_id(int role_id) {
    this.role_id = role_id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}