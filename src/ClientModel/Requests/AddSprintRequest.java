package ClientModel.Requests;

import DataModel.Employee;
import DataModel.Project;

import java.sql.Date;

public class AddSprintRequest extends Request
{
  private Project project;
  private String name;
  private Date startDate, endDate;

  public AddSprintRequest(Employee employee, Project project, String name, Date startDate, Date endDate)
  {
    super("addSprint", employee);
    this.project = project;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Project getProject()
  {
    return project;
  }

  public String getName()
  {
    return name;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }
}
