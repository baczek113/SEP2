package Model;

public class Task {
  private int task_id;
  private Integer sprint_id;
  private int created_by;
  private int project_id;
  private String title;
  private String description;
  private String status;
  private Integer priority;

  public Task(int task_id, Integer sprint_id, int created_by, int project_id,
      String title, String description, String status, Integer priority) {
    this.task_id = task_id;
    this.sprint_id = sprint_id;
    this.created_by = created_by;
    this.project_id = project_id;
    this.title = title;
    this.description = description;
    this.status = status;
    this.priority = priority;
  }

  public Task() {
  }

  public int getTask_id() {
    return task_id;
  }

  public Integer getSprint_id() {
    return sprint_id;
  }

  public int getCreated_by() {
    return created_by;
  }

  public int getProject_id() {
    return project_id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getStatus() {
    return status;
  }

  public Integer getPriority() {
    return priority;
  }

  // Setters
  public void setTask_id(int task_id) {
    this.task_id = task_id;
  }

  public void setSprint_id(Integer sprint_id) {
    this.sprint_id = sprint_id;
  }

  public void setCreated_by(int created_by) {
    this.created_by = created_by;
  }

  public void setProject_id(int project_id) {
    this.project_id = project_id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }
}