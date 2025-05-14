package ClientModel.Requests;

import Model.Employee;
import Model.Sprint;

public class AddTaskRequest extends Request
{
  private Sprint sprint;
  private String name, description;
  private int priority;

  public AddTaskRequest(String action, Employee employee, Sprint sprint, String name, String description, int priority)
  {
    super("addtask", employee);
    this.sprint = sprint;
    this.name = name;
    this.description = description;
    this.priority = priority;
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
