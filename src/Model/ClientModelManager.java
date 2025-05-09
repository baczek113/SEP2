package Model;

import Network.Response;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientModelManager {
  private ClientConnection clientConnection;
  private List<Project> projects;
  private List<Sprint> sprints;
  private List<Task> tasks;

  public ClientModelManager(ClientConnection clientConnection) {
    this.clientConnection = clientConnection;
    this.projects = new ArrayList<>();
    this.sprints = new ArrayList<>();
    this.tasks = new ArrayList<>();
  }

  //hardcoded values for now
  public void createProject(String name, String description) {
    Project project = new Project(1, 001, "sleep", "needa sleep", "Ongoing",
        new Date(2025, 8, 9), new Date(2025, 9, 9));
    Request request = new Request("project", "create", project, null);
    clientConnection.sendRequest(request);
  }

  public void createSprint(String name, String projectId) {
    Sprint sprint = new Sprint(1, 001, "sleep", new Date(2025, 8, 9), new Date(2025, 9, 9));
    Request request = new Request("sprint", "create", sprint, projectId);
    clientConnection.sendRequest(request);
  }

  public void createTask(String title, String status, String sprintId) {
    Task task = new Task(1, 1, 001, 1, "sleep", "need it", "Ongoing", 5);
    Request request = new Request("task", "create", task, sprintId);
    clientConnection.sendRequest(request);
  }

  public void handleResponse(Response response)
  {
    List<Project> newProjects = response.getProjects();
    List<Sprint> newSprints = response.getSprints();
    List<Task> newTasks = response.getTasks();

    projects.clear();
    projects.addAll(newProjects);

    sprints.clear();
    sprints.addAll(newSprints);
    tasks.addAll(newTasks);
  }
}