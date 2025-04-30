package Model;

public class FinishedState implements ProjectState {
  @Override
  public boolean onPending(Project project, int employeeId) {
    return false;
  }

  @Override
  public boolean onOngoing(Project project, int employeeId) {
    return false;
  }

  @Override
  public boolean onLate(Project project, int employeeId) {
    return false;
  }

  @Override
  public boolean onFinished(Project project, int employeeId) {
    return true;
  }

  @Override
  public String getState() {
    return "finished";
  }

  @Override
  public String getStateDescription() {
    return "Project is completed.";
  }
}