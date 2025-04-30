package Network;

public class Task {
    private int task_id;
    private int sprint_id;
    private int created_by;
    private int project_id;
    private String title;
    private String description;
    private String status;
    private int priority;

    public Task(int task_id, int sprint_id, int created_by, int project_id, String title, String description, String status, int priority) {
        this.task_id = task_id;
        this.sprint_id = sprint_id;
        this.created_by = created_by;
        this.title = title;
        this.project_id = project_id;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getSprint_id() {
        return sprint_id;
    }

    public void setSprint_id(int sprint_id) {
        this.sprint_id = sprint_id;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
