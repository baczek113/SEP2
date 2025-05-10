package Model;

import java.util.ArrayList;

public class Task {
    private int task_id;
    private Sprint sprint;
    private Employee created_by;
    private String title;
    private String description;
    private String status;
    private int priority;
    private ArrayList<Employee> assignedTo;

    public Task(int task_id, Sprint sprint, Employee created_by, String title, String description, String status, int priority) {
        this.task_id = task_id;
        this.sprint = sprint;
        this.created_by = created_by;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.assignedTo = new ArrayList<Employee>();
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Employee getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Employee created_by) {
        this.created_by = created_by;
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

    public void assignTo(Employee employee) {
        assignedTo.add(employee);
    }

    public void unassignTo(Employee employee) {
        assignedTo.remove(employee);
    }

    public ArrayList<Employee> getAssignedTo() {
        return assignedTo;
    }
}
