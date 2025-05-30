package DataModel;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Sprint implements Serializable {
    private int sprint_id;
    private int project_id;
    private String name;
    private Date start_date;
    private Date end_date;
    private List<Task> tasks;

    public Sprint(int sprint_id, int project_id, String name, Date start_date, Date end_date) {
        this.sprint_id = sprint_id;
        this.project_id = project_id;
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

    public int getProject_id() {
        return project_id;
    }

    public void setProject(int project_id) {
        this.project_id = project_id;
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

    public List<Task> getTasks()
    {
        return tasks;
    }
}
