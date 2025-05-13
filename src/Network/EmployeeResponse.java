package Network;

import Network.Utils.EmployeeList;

import java.io.Serializable;

public class EmployeeResponse extends Response implements Serializable
{
  private String statusMesssage;
  private EmployeeList employees;

  public EmployeeResponse(String statusMesssage, EmployeeList employeeList){
    super(statusMesssage);
    this.employees = employeeList;
  }

  public String getMesssage(){
    return statusMesssage;
  }

  public EmployeeList getEmployees(){
    return employees;
  }
}
