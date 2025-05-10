package ClientModel.ServerInteractions;

import Model.Employee;

public class LoginRequest extends Request
{

  private String username, password;

  public LoginRequest(String action, Employee employee, String username, String password)
  {
    super(action, employee);
    this.username = username;
    this.password = password;
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
