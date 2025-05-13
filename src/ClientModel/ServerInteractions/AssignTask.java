package ClientModel.ServerInteractions;

import Model.Employee;
import Model.Task;

public class AssignTask extends Request
{
  private Employee employeeAssigned;
  private Task task;

  public AssignTask(String action, Employee employee, Task task)
  {
    super(action = "assigntask", employee);
    this.task = task;
  }

  public Employee getEmployeeAssigned()
  {
    return employeeAssigned;
  }

  public Task getTask()
  {
    return task;
  }
}
