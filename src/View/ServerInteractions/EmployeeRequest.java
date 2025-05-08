package View.ServerInteractions;

import Model.Employee;

public class EmployeeRequest extends Request
{
  private Employee employee;

  public EmployeeRequest(String action, Employee employee)
  {
    super(action);
    this.employee = employee;
  }

  public Employee getEmployee(){
    return this.employee;
  }
}
