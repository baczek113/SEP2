package Network.Response;

import Model.Employee;

import java.io.Serializable;
import java.util.List;

public class EmployeeResponse extends Response implements Serializable
{
  private List<Employee> employees;

  public EmployeeResponse(String statusMesssage, List<Employee> employeeList){
    super(statusMesssage);
    this.employees = employeeList;
  }

  public List<Employee> getEmployees(){
    return employees;
  }
}
