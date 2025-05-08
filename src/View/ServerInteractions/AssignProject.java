package View.ServerInteractions;

import Model.Employee;
import Model.Project;

public class AssignProject extends Request
{
  private Project project;
  private Employee employee;

  public AssignProject(String action, Project project, Employee employee)
  {
    super(action);
    this.project = project;
    this.employee = employee;
  }

  public Project getProject()
  {
    return project;
  }

  public Employee getEmployee()
  {
    return employee;
  }
}
