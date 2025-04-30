package Model;

public interface ProjectState
{
  boolean onPending (Project project, int employeeID);
  boolean onOngoing (Project project, int employeeID);
  boolean onLate (Project project, int employeeID);
  boolean onFinished (Project project, int employeeID);

}
