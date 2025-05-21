package Network.Response;

import DataModel.Employee;

public class LoginResponse extends Response
{
  private Employee employee;

  public LoginResponse(String statusMessage, Employee employee)
  {
    super(statusMessage);
    this.employee = employee;
  }

  public Employee getEmployee(){
    return this.employee;
  }

}
