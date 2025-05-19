package ViewModel;

import ClientModel.ClientModelManager;
import Model.Employee;
import Model.Project;

import java.util.List;

public class EditProjectViewModel {
    private final ClientModelManager model;
    private Project project;

    public EditProjectViewModel(ClientModelManager model) {
        this.model = model;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void startProject() {
        model.sendProject("startProject", project);
    }

    public void endProject() {
        model.sendProject("endProject", project);
    }

    private void addEmployee(Employee employee)
    {
        model.addEmployeeToProject("addEmployeeToProject", project, employee);
    }

    private void removeEmployee(Employee employee)
    {
        model.addEmployeeToProject("removeEmployeeFromProject", project, employee);
    }

    public void saveEmployees(List<Employee> employeesAssigned, List<Employee> employeesNotAssigned)
    {
        for(Employee employee : employeesAssigned)
        {
            if(project.getEmployees().get(employee.getEmployee_id()) == null)
            {
                addEmployee(employee);
            }
        }
        for(Employee employee : employeesNotAssigned)
        {
            if(project.getEmployees().get(employee.getEmployee_id()) != null)
            {
                removeEmployee(employee);
            }
        }
    }

    public void saveNameAndDesc(String name, String desc)
    {
        Project projectCopy = new Project(project.getProject_id(), null, null, name, desc, null, null, null);
        model.sendProject("editProject", projectCopy);
    }

    public List<Employee> getEmployees()
    {
        return model.getEmployees();
    }
}
