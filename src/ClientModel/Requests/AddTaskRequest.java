package ClientModel.Requests;

import DataModel.Employee;
import DataModel.Project;

public class AddTaskRequest extends Request
{
  private Project project;
  private String name, description;
  private int priority;

  public AddTaskRequest(Employee employee, Project project, String name, String description, int priority)
  {
    super("addTask", employee);
    this.project = project;
    this.name = name;
    this.description = description;
    this.priority = priority;
  }

  public Project getProject(){
    return this.project;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public int getPriority()
  {
    return priority;
  }
}
