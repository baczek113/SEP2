package Network.Response;

import DataModel.Project;

import java.io.Serializable;
import java.util.List;

public class ProjectResponse extends Response implements Serializable
{
  private List<Project> projects;

  public ProjectResponse(List<Project> projects){
    super("project");
    this.projects = projects;
  }

  public List<Project> getProjects(){
    return projects;
  }
}
