package Network.Response;

import DataModel.Employee;

import java.io.Serializable;
import java.util.List;

public class EmployeeResponse extends Response implements Serializable
{
  private List<Employee> employees;

  public EmployeeResponse(List<Employee> employeeList){
    super("employee");
    this.employees = employeeList;
  }

  public List<Employee> getEmployees(){
    return employees;
  }
}
