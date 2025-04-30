package Model;

public class LateState implements ProjectState {
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
    return true;
  }

  @Override
  public boolean onFinished(Project project, int employeeId) {
    project.setState(new FinishedState());
    project.setStatus("finished");
    return true;
  }

  @Override
  public String getState() {
    return "late";
  }

  @Override
  public String getStateDescription() {
    return "Project is behind schedule.";
  }
}