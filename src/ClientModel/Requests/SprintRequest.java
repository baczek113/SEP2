package ClientModel.Requests;

import Model.Sprint;
import Model.Employee;

public class SprintRequest extends Request
{
  private Sprint sprint;

  public SprintRequest(String action, Employee employee, Sprint sprint)
  {
    super("sprintrequest", employee);
    this.sprint = sprint;
  }

  public Sprint getSprint()
  {
    return sprint;
  }
}
