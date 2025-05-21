package ViewModel;

import ClientModel.ClientModelManager;
import DataModel.Employee;
import DataModel.Project;
import DataModel.Task;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class ManageBacklogViewModel {
    private final ClientModelManager model;
    private PropertyChangeSupport propertyChangeSupport;

    public ManageBacklogViewModel(ClientModelManager model) {
        this.model = model;
        propertyChangeSupport = new PropertyChangeSupport(this);
        model.addListener("projects", this::projectsUpdated);
    }

    public void addListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public Employee getLoggedEmployee()
    {
        return model.getLoggedEmployee();
    }

    public List<Project> getProjects()
    {
        return model.getProjects();
    }

    public void projectsUpdated(PropertyChangeEvent e) {
        propertyChangeSupport.firePropertyChange("projects", null, null);
    }

    public void remove(Task task)
    {
        model.removeTask(task);
    }
}
