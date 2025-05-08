package View.ServerInteractions;

import Model.Sprint;

public class SprintRequest extends Request
{
  private Sprint sprint;

  public SprintRequest(String action, Sprint sprint)
  {
    super(action);
    this.sprint = sprint;
  }

  public Sprint getSprint()
  {
    return sprint;
  }
}
