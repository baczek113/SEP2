package ViewModel;

import ClientModel.ClientModelManager;
import Model.Employee;
import Model.Project;
import Model.Task;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class EditProjectViewModel {
    private final ClientModelManager model;
    private PropertyChangeSupport propertyChangeSupport;

    public EditProjectViewModel(ClientModelManager model) {
        this.model = model;
        propertyChangeSupport = new PropertyChangeSupport(this);
        model.addListener("projects", this::projectsUpdated);
    }

    public void addListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public Project getProject(int project_id)
    {
        for(Project project : model.getProjects())
        {
            if(project.getProject_id() == project_id)
            {
                return project;
            }
        }
        return null;
    }

    public void projectsUpdated(PropertyChangeEvent e) {
        propertyChangeSupport.firePropertyChange("projects", null, null);
    }

    public void startProject(Project project) {
        model.sendProject("startProject", project);
    }

    public void endProject(Project project) {
        model.sendProject("endProject", project);
    }
}
