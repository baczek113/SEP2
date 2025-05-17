package ClientModel.Requests;

import Model.Employee;
import Model.Task;

public class AssignTaskRequest extends Request
{
  private Task task;

  public AssignTaskRequest(String action, Employee employee, Task task)
  {
    super(action, employee);
    this.task = task;
  }

  public Task getTask()
  {
    return task;
  }
}
