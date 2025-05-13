package Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private int project_id;
    private Employee created_by;
    private Employee scrum_master;
    private String name;
    private String description;
    private String status;
    private Date start_date;
    private Date end_date;
    private List<Employee> employees; //List containing all the project participants incl. the product owner
    private List<Sprint> sprints; //List containing all the project sprints
    private List<Task> backlog;

    public Project(int project_id, Employee created_by, Employee scrum_master, String name, String description, String status, Date start_date, Date end_date) {
        this.project_id = project_id;
        this.created_by = created_by;
        this.scrum_master = scrum_master;
        this.name = name;
        this.description = description;
        this.status = status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.employees = new EmployeeList();
        this.sprints = new ArrayList<Sprint>();
        this.backlog = new ArrayList<Task>();
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Employee getCreated_by() {
        return created_by;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setCreated_by(Employee created_by) {
        this.created_by = created_by;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public void addSprint(Sprint sprint)
    {
        this.sprints.add(sprint);
    }

    public void removeEmployee(Employee employee)
    {
        this.employees.remove(employee);
    }

    public void removeSprint(Sprint sprint)
    {
        this.sprints.remove(sprint);
    }

    public List<Sprint> getSprints()
    {
        return sprints;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Task> getBacklog() {
        return backlog;
    }

    public void addToBacklog(Task task)
    {
        this.backlog.add(task);
    }

    public Employee getScrum_master()
    {
        return scrum_master;
    }

    public void setScrum_master(Employee scrum_master) {
        this.scrum_master = scrum_master;
    }
}
