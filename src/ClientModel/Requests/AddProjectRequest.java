package ClientModel.Requests;

import Model.Employee;

import java.sql.Date;
import java.util.List;

public class AddProjectRequest extends Request {
    private Employee scrum_master;
    private String name, description;
    private Date start_date, end_date;
    private List<Employee> assignees;

    public AddProjectRequest(Employee employee, Employee scrum_master, String name, String description, Date start_date, Date end_date, List<Employee> assignees) {
        super("addProject", employee);
        this.scrum_master = scrum_master;
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.assignees = assignees;
    }

    public String getName() {
        return name;
    }

    public Employee getScrum_master() {
        return scrum_master;
    }

    public String getDescription() {
        return description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public List<Employee> getAssignees() {
        return assignees;
    }
}
