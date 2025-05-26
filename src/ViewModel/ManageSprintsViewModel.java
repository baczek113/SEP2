package ViewModel;

import ClientModel.ClientModelManager;
import DataModel.Employee;
import DataModel.Project;
import DataModel.Sprint;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ManageSprintsViewModel
{
  private final ClientModelManager model;
  private PropertyChangeSupport propertyChangeSupport;

  public ManageSprintsViewModel(ClientModelManager client)
  {
    this.model = client;
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
      propertyChangeSupport.firePropertyChange("projects", null, null);
  }
  public Project getProject(int project_id) {
    return model.getProjects().get(project_id);
  }
  public void remove(Sprint sprint){
    model.removeSprint(sprint);
  }
}
