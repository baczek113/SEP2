package Network;

import Model.Project;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectResponse extends Response implements Serializable
{
  private ArrayList<Project> projects;

  public ProjectResponse(String statusMessage, ArrayList<Project> projects){
    super(statusMessage);
    this.projects = projects;
  }

  public ArrayList<Project> getProjects(){
    return projects;
  }
}
