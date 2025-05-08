package View.ServerInteractions;

import Model.Employee;
import Model.Task;

public class AssignTask extends Request
{
  private Employee employee;
  private Task task;

  public AssignTask(String action, Employee employee, Task task)
  {
    super(action);
    this.employee = employee;
    this.task = task;
  }

  public Employee getEmployee()
  {
    return employee;
  }

  public Task getTask()
  {
    return task;
  }
}
