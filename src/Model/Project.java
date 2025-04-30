package Model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Project implements Serializable {
  private int project_id;
  private int created_by;
  private String name;
  private String description;
  private String status;
  private Date start_date;
  private Date end_date;
  private ArrayList<Task> tasks;
  private ArrayList<Sprint> sprints;
  private ProjectState currentState;


  public Project(int project_id, int created_by, String name, String description,
      String status, Date start_date, Date end_date) {
    this.project_id = project_id;
    this.created_by = created_by;
    this.name = name;
    this.description = description;
    this.status = status;
    this.start_date = start_date;
    this.end_date = end_date;
    this.tasks = new ArrayList<>();
    this.sprints = new ArrayList<>();
    this.currentState = new PendingState();
  }

  public Project() {
    this.tasks = new ArrayList<>();
    this.sprints = new ArrayList<>();
    this.currentState = new PendingState();
  }

  public boolean onPending(int employeeId) {
    return currentState.onPending(this, employeeId);
  }

  public boolean onOngoing(int employeeId) {
    return currentState.onOngoing(this, employeeId);
  }

  public boolean onLate(int employeeId) {
    return currentState.onLate(this, employeeId);
  }

  public boolean onFinished(int employeeId) {
    return currentState.onFinished(this, employeeId);
  }


  public String getState() {
    return currentState.getState();
  }

  public String getStateDescription() {
    return currentState.getStateDescription();
  }

  void setState(ProjectState newState) {
    this.currentState = newState;
  }


  public int getProject_id() {
    return project_id;
  }

  public int getCreated_by() {
    return created_by;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getStatus() {
    return status;
  }

  public Date getStart_date() {
    return start_date;
  }

  public Date getEnd_date() {
    return end_date;
  }

  public ArrayList<Task> getTasks() {
    return tasks;
  }

  public ArrayList<Sprint> getSprints() {
    return sprints;
  }

  public void setProject_id(int project_id) {
    this.project_id = project_id;
  }

  public void setCreated_by(int created_by) {
    this.created_by = created_by;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setStart_date(Date start_date) {
    this.start_date = start_date;
  }

  public void setEnd_date(Date end_date) {
    this.end_date = end_date;
  }

  public void setTasks(ArrayList<Task> tasks) {
    this.tasks = tasks;
  }

  public void setSprints(ArrayList<Sprint> sprints) {
    this.sprints = sprints;
  }
}