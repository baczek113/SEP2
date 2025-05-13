package Model;

import java.io.Serializable;
import java.util.List;

public class Task implements Serializable {
    private int task_id;
    private int sprint_id;
    private int project_id;
    private String title;
    private String description;
    private String status;
    private int priority;
    private List<Employee> assignedTo;

    public Task(int task_id, int sprint_id, int project_id, String title, String description, String status, int priority) {
        this.task_id = task_id;
        this.sprint_id = sprint_id;
        this.project_id = project_id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.assignedTo = new EmployeeList();
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getSprint() {
        return sprint_id;
    }

    public void setSprint(int sprint_id) {
        this.sprint_id = sprint_id;
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

    public int getSprint_id()
    {
        return sprint_id;
    }

    public void setSprint_id(int sprint_id)
    {
        this.sprint_id = sprint_id;
    }

    public int getProject_id()
    {
        return project_id;
    }

    public void setProject_id(int project_id)
    {
        this.project_id = project_id;
    }

    public void assignTo(Employee employee) {
        assignedTo.add(employee);
    }

    public void unassignTo(Employee employee) {
        assignedTo.remove(employee);
    }

    public List<Employee> getAssignedTo() {
        return assignedTo;
    }
}
