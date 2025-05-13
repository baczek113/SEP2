package View;

import java.util.ArrayList;
import java.util.List;

public class Sprint {
    private String name;
    private String startDate;
    private String endDate;
    private List<Task> tasks;

    public Sprint(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public List<Task> getTasks() { return tasks; }

    public void addTask(Task task) {
        tasks.add(task);
    }
}
