package Network.Response;

import Model.Project;

import java.io.Serializable;
import java.util.List;

public class ProjectResponse extends Response implements Serializable
{
  private List<Project> projects;

  public ProjectResponse(String statusMessage, List<Project> projects){
    super(statusMessage);
    this.projects = projects;
  }

  public List<Project> getProjects(){
    return projects;
  }
}
