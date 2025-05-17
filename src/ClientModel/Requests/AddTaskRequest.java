package ClientModel.Requests;

import Model.Employee;
import Model.Project;
import Model.Sprint;

public class AddTaskRequest extends Request
{
  private Project project;
  private Sprint sprint;
  private String name, description;
  private int priority;

  public AddTaskRequest(Employee employee, Project project, Sprint sprint, String name, String description, int priority)
  {
    super("addTask", employee);
    this.project = project;
    this.sprint = sprint;
    this.name = name;
    this.description = description;
    this.priority = priority;
  }

  public Project getProject(){
    return this.project;
  }
  public Sprint getSprint()
  {
    return sprint;
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
