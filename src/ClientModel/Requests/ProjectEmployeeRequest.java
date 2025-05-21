package ClientModel.Requests;

import DataModel.Employee;
import DataModel.Project;

public class ProjectEmployeeRequest extends Request{
    private Project project;
    private Employee employeeToAdd;

    public ProjectEmployeeRequest(String action, Employee employee, Project project, Employee employeeToAdd) {
        super(action, employee);
        this.project = project;
        this.employeeToAdd = employeeToAdd;
    }

    public Employee getEmployeeToAdd() {
        return employeeToAdd;
    }

    public Project getProject() {
        return project;
    }
}
