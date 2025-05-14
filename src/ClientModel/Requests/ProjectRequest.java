package ClientModel.Requests;

import Model.Employee;
import Model.Project;

public class ProjectRequest extends Request{
    private Project project;

    public ProjectRequest(String action, Employee employee, Project project){
        super("projectrequest", employee);
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}
