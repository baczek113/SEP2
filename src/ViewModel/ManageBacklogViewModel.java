package ViewModel;

import ClientModel.ClientModelManager;
import Model.Employee;
import Model.Project;
import Model.ProjectList;
import Model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
