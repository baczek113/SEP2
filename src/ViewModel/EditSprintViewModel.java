package ViewModel;

import ClientModel.ClientModelManager;
import Model.Project;
import Model.Sprint;
import Model.Task;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class EditSprintViewModel {
  private final ClientModelManager model;
  private List<Project> projects;
  private PropertyChangeSupport propertyChangeSupport;

  public EditSprintViewModel(ClientModelManager model)
  {
    this.model = model;
    projects = model.getProjects();
    propertyChangeSupport = new PropertyChangeSupport(this);
    model.addListener("projects", this::projectsUpdated);
  }

  public void editSprint(Sprint sprint){
      model.editSprint(sprint);
  }

  public void addTaskToSprint(String action, Sprint sprint, Task task){
    model.addTaskToSprint(action, sprint, task);
  }

  public void removeTaskFromSprint(Task task, Sprint sprint){
    model.removeTaskFromSprint(task, sprint);
  }

  public void addListener(PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(listener);
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
}
