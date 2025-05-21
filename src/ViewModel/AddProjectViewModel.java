package ViewModel;

import ClientModel.ClientModelManager;
import DataModel.Employee;

import java.util.List;

public class AddProjectViewModel {
    private final ClientModelManager model;

    public AddProjectViewModel(ClientModelManager model) {
        this.model = model;
    }

    public List<Employee> getEmployees()
    {
        return model.getEmployees();
    }

    public void saveProject(Employee scrum_master, String name, String desc, List<Employee> assignees)
    {
        model.addProject(scrum_master, name, desc, null, null, assignees);
    }

    public Employee getLoggedEmployee()
    {
        return model.getLoggedEmployee();
    }
}
