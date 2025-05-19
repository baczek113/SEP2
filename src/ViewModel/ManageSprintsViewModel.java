package ViewModel;

import ClientModel.ClientModelManager;
import Model.Employee;
import Model.Project;
import Model.Sprint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ManageSprintsViewModel
{
  private final ClientModelManager model;
  private List<Project> projects;
  private PropertyChangeSupport propertyChangeSupport;

  public ManageSprintsViewModel(ClientModelManager client)
  {
    this.model = client;
    projects = client.getProjects();
    propertyChangeSupport = new PropertyChangeSupport(this);
    model.addListener("projects", this::projectsUpdated);
  }

  public void addListener(PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(listener);
  }

  public Employee getLoggedEmployee(){
    return model.getLoggedEmployee();
  }
  public void projectsUpdated(PropertyChangeEvent e) {
      List<Project> updatedProjectsFromModel = (List<Project>) e.getNewValue();
      this.projects = new ArrayList<>(updatedProjectsFromModel);
      propertyChangeSupport.firePropertyChange("projects", null, null);
  }
  public Project getProject(int project_id) {
    for(Project project : model.getProjects())
    {
      if(project.getProject_id() == project_id)
      {
        return project;
      }
    }
    return null;
  }
  public void remove(Sprint sprint){
    model.removeSprint(sprint);
  }
}
