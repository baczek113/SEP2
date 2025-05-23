package ViewModel;

import ClientModel.ClientModelManager;
import DataModel.Project;

public class AddTaskViewModel {
    private final ClientModelManager model;

    public AddTaskViewModel(ClientModelManager model) {
        this.model = model;
    }

    public void addTask(Project project, String name, String description, int priority) {
        model.addTask(project, name, description, priority);
    }
}
