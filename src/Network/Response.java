package Network;

import Model.Project;
import Model.Sprint;
import Model.Task;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable
{
    private List<Project> projects; //for now just like vinyls, the server will send back the whole data so that the client model takes it and replaces it locally
    private List<Sprint> sprints;
    private List<Task> tasks;
    private String message;

    public Response(List<Project> projects, List<Sprint> sprints, List<Task> tasks) {
        this.projects = projects;
        this.sprints = sprints;
        this.tasks = tasks;
    }

    public Response(String message) {
        this.message = message;
    }

    public List<Project> getProjects() { return projects; }
    public List<Sprint> getSprints() { return sprints; }
    public List<Task> getTasks() { return tasks; }
    public String getMessage() { return message; }
}