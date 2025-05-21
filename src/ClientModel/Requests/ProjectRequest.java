package ClientModel.Requests;

import DataModel.Employee;
import DataModel.Project;

public class ProjectRequest extends Request{
    private Project project;

    public ProjectRequest(String action, Employee employee, Project project){
        super(action, employee);
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}
