package Network;

import Model.EmployeeList;

import java.io.Serializable;

public class EmployeeResponse extends Response implements Serializable
{
  private EmployeeList employees;

  public EmployeeResponse(String statusMesssage, EmployeeList employeeList){
    super(statusMesssage);
    this.employees = employeeList;
  }

  public EmployeeList getEmployees(){
    return employees;
  }
}
