package ViewModel;

import ClientModel.ClientModelManager;
import Model.Project;
import Model.Task;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ManageTasksViewModel {
    private final ClientModelManager model;
    private final PropertyChangeSupport propertyChangeSupport;

    public ManageTasksViewModel(ClientModelManager model){
        this.model = model;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        model.addListener("projects", this::updateProjects);
    }

    private void updateProjects(PropertyChangeEvent propertyChangeEvent) {
        propertyChangeSupport.firePropertyChange("projects", null, null);
    }

    public Project getProject(int id){
        for(Project project : model.getProjects())
        {
            if(project.getProject_id() == id)
            {
                return project;
            }
        }
        return null;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void takeTask(Task task)
    {
        model.assignTask("assignTask", task);
    }

    public void unassignTask(Task task)
    {
        model.assignTask("unassignTask", task);
    }

    public String getLoggedEmployeeRole()
    {
        return model.getLoggedEmployee().getRole().getRole_name();
    }

    public void changeTaskStatus(Task task, String status)
    {
        model.changeTaskStatus(task, status);
    }

    public int getLoggedEmployeeId()
    {
        return model.getLoggedEmployee().getEmployee_id();
    }
}
