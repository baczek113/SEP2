package ViewModel;

import ClientModel.ClientModelManager;
import Model.Project;
import Model.ProjectList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class ManageProjectsViewModel {
    private final ClientModelManager model;
    private List<Project> projects;
    private PropertyChangeSupport propertyChangeSupport;

    public ManageProjectsViewModel(ClientModelManager model) {
        this.model = model;
        projects = model.getProjects();
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
