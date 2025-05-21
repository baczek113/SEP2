package ViewModel;

import ClientModel.ClientModelManager;
import DataModel.Project;
import DataModel.Task;

public class EditTaskViewModel {
    private final ClientModelManager model;

    public EditTaskViewModel(ClientModelManager model) {
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

    public void saveEdition(Task task)
    {
        model.editTask(task);
    }

    public void assignPriority(Task task, int priority)
    {
        model.assignPriority(task, priority);
    }
}
