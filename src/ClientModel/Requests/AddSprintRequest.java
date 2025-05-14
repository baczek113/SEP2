package ClientModel.Requests;

import Model.Employee;
import Model.Project;

import java.util.Date;

public class AddSprintRequest extends Request
{
  private Project project;
  private String name;
  private String description;
  private Date startDate, endDate;

  public AddSprintRequest(String action, Employee employee, Project project, String name, String description, Date startDate, Date endDate)
  {
    super("addsprint", employee);
    this.project = project;
    this.name = name;
    this.description = description;
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

  public String getDescription()
  {
    return description;
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
