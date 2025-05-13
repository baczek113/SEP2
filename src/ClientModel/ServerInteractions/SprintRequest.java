package ClientModel.ServerInteractions;

import Model.Sprint;
import Model.Employee;

public class SprintRequest extends Request
{
  private Sprint sprint;

  public SprintRequest(String action, Employee employee, Sprint sprint)
  {
    super(action = "sprintrequest", employee);
    this.sprint = sprint;
  }

  public Sprint getSprint()
  {
    return sprint;
  }
}
