package View.ServerInteractions;

import Model.Employee;
import Model.Sprint;

public class AddTaskRequest extends Request
{
  private Employee employee;
  private Sprint sprint;
  private String name, description;
  private int priority;

  public AddTaskRequest(String action, Employee employee, Sprint sprint, String name, String description, int priority)
  {
    super(action);
    this.employee = employee;
    this.sprint = sprint;
    this.name = name;
    this.description = description;
    this.priority = priority;
  }

  public Employee getEmployee()
  {
    return employee;
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
