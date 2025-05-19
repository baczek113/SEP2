package ViewModel;

import ClientModel.ClientModelManager;
import Model.Project;
import Model.Sprint;
import Model.Task;

import java.beans.PropertyChangeSupport;
import java.sql.Date;

public class AddSprintViewModel {
    private final ClientModelManager model;
    private PropertyChangeSupport propertyChangeSupport;

    public AddSprintViewModel(ClientModelManager model){
        this.model = model;
    }

    public void addSprint(Project project, String name, Date startDate, Date endDate){
        model.addSprint(project, name, startDate, endDate);
    }
    public void addTaskToSprint(String action, Sprint sprint, Task task){
        model.addTaskToSprint(action, sprint, task);
    }
}
