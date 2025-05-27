package ClientModel.Requests;

import DataModel.Employee;

public class LoginRequest extends Request
{

  private String username, password;

  public LoginRequest(String action, Employee employee, String username, String password)
  {
    super(action, null);
    this.username = username;
    this.password = password;
    System.out.println("Login LoginRequest");
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

}
