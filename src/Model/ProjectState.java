package Model;

public interface ProjectState {
  boolean onPending(Project project, int employeeId);
  boolean onOngoing(Project project, int employeeId);
  boolean onLate(Project project, int employeeId);
  boolean onFinished(Project project, int employeeId);
  String getState();
  String getStateDescription();
}

