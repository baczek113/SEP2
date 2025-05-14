package View;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private String status;
    private String startDate;
    private String endDate;
    private List<Sprint> sprints;
    private List<Task> tasks;

    public Project(String name, String status, String startDate, String endDate) {
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sprints = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getStatus() { return status; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public List<Sprint> getSprints() { return sprints; }
    public List<Task> getTasks() { return tasks; }

    public void addSprint(Sprint sprint) {
        sprints.add(sprint);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}
