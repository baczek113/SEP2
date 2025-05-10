package Model;

import java.sql.Date;
import java.util.ArrayList;

public class Sprint {
    private int sprint_id;
    private Project project;
    private String name;
    private Date start_date;
    private Date end_date;
    private ArrayList<Task> tasks;

    public Sprint(int sprint_id, Project project, String name, Date start_date, Date end_date) {
        this.sprint_id = sprint_id;
        this.project = project;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.tasks = new ArrayList<Task>();
    }

    public int getSprint_id() {
        return sprint_id;
    }

    public void setSprint_id(int sprint_id) {
        this.sprint_id = sprint_id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public ArrayList<Task> getTasks()
    {
        return tasks;
    }
}
