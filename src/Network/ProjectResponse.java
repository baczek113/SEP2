package Network;

import Model.Project;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectResponse extends Response implements Serializable
{
  private String message;
  private ArrayList<Project> projects;

  public ProjectResponse(String message, ArrayList<Project> projects){
    super(message);
    this.projects = projects;
  }

  public String getMessage(){
    return message;
  }

  public ArrayList<Project> getProjects(){
    return projects;
  }
}
