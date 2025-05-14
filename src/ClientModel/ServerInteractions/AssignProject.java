package ClientModel.ServerInteractions;

import Model.Employee;
import Model.Project;

public class AssignProject extends Request
{
  private Project project;
  private Employee employeeToAssign;

  public AssignProject(String action, Employee employee, Project project, Employee employeeToAssign)
  {
    super("assignproject", employee);
    this.project = project;
    this.employeeToAssign = employeeToAssign;
  }

  public Project getProject()
  {
    return project;
  }

  public Employee getEmployeeToAssign()
  {
    return employeeToAssign;
  }
}
