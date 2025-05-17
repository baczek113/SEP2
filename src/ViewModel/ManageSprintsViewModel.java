package ViewModel;

import ClientModel.ClientModelManager;
import Model.Project;
import Model.Sprint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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

  public void projectsUpdated(PropertyChangeEvent e) {
    projects = (List<Project>) e.getNewValue();
    propertyChangeSupport.firePropertyChange("projects", null, null);
  }
  public ObservableList<Project> getProjects() {
    ObservableList<Project> projectsObservable = FXCollections.observableArrayList();
    projectsObservable.setAll(projects);
    return projectsObservable;
  }
}
