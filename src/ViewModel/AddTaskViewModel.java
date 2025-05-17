package ViewModel;

import ClientModel.ClientModelManager;
import Model.Project;

import java.beans.PropertyChangeSupport;

public class AddTaskViewModel {
    private final ClientModelManager model;
    private PropertyChangeSupport propertyChangeSupport;

    public AddTaskViewModel(ClientModelManager model) {
        this.model = model;
    }

    public void addTask(Project project, String name, String description, int priority) {
        model.addTask(project, name, description, priority);
    }
}
