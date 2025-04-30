package Model;

public class PendingState implements ProjectState {
  @Override
  public boolean onPending(Project project, int employeeId) {
    return true;
  }

  @Override
  public boolean onOngoing(Project project, int employeeId) {
    project.setState(new OngoingState());
    project.setStatus("ongoing");
    return true;
  }

  @Override
  public boolean onLate(Project project, int employeeId) {
    return false;
  }

  @Override
  public boolean onFinished(Project project, int employeeId) {
    return false;
  }

  @Override
  public String getState() {
    return "pending";
  }

  @Override
  public String getStateDescription() {
    return "Project is pending approval or start.";
  }
}