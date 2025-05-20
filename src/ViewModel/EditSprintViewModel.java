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
  private PropertyChangeSupport propertyChangeSupport;
  private Sprint sprint;

  public EditSprintViewModel(ClientModelManager model)
  {
    this.model = model;
  }

  public void setSprint(Sprint sprint){
    this.sprint = sprint;
  }
  public void editSprint(Sprint sprint){
      model.editSprint(sprint);
  }

  public void addTasks(List<Task> tasksAssigned, List<Task> tasksAvailable){
    for (Task taskAssigned : tasksAssigned){
      if (taskAssigned.getSprint_id() != sprint.getSprint_id()) {
        model.addTaskToSprint("addTaskToSprint", sprint, taskAssigned);
      }
    }
    for (Task availableTask : tasksAvailable){
      if (availableTask.getSprint_id() == sprint.getSprint_id()) {
        model.removeTaskFromSprint(availableTask, sprint);
      }
    }
  }
}
