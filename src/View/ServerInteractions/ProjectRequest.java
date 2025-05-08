package View.ServerInteractions;

import Model.Project;

public class ProjectRequest extends Request{
    private Project project;

    public ProjectRequest(String action, Project project){
        super(action);
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}
