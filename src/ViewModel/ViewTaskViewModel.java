package ViewModel;

import ClientModel.ClientModelManager;
import Model.Employee;
import Model.Project;
import Model.Task;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class ViewTaskViewModel {
    private final ClientModelManager model;

    public ViewTaskViewModel(ClientModelManager model) {
        this.model = model;
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
    public Employee getLoggedEmployee ()
    {
        return model.getLoggedEmployee();
    }
}
