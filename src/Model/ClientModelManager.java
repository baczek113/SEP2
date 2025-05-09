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

  public void retrieveProject(String projectId) {
    Request request = new Request("project", "retrieve", projectId, null);
    clientConnection.sendRequest(request);
  }

  public void listSprints(String projectId) {
    Request request = new Request("sprint", "list", null, projectId);
    clientConnection.sendRequest(request);
  }

  public void listTasks(String sprintId) {
    Request request = new Request("task", "list", null, sprintId);
    clientConnection.sendRequest(request);
  }

  public void handleServerResponse(Response response) {
    if (response.getData() != null) {
      Object result = response.getData();
      if (result instanceof String) {

        String id = (String) result;

        System.out.println("Received ID: " + id);
      } else if (result instanceof Project) {
        Project project = (Project) result;
        updateProject(project);
      } else if (result instanceof Sprint) {
        Sprint sprint = (Sprint) result;
        updateSprint(sprint);
      } else if (result instanceof List) {
        List<?> list = (List<?>) result;
        if (!list.isEmpty()) {
          if (list.get(0) instanceof Project) {
            updateProjects((List<Project>) list);
          } else if (list.get(0) instanceof Sprint) {
            updateSprints((List<Sprint>) list);
          } else if (list.get(0) instanceof Task) {
            updateTasks((List<Task>) list);
          }
        }
      }
    } else {
      System.err.println("Error from server: " + response.getMessage());
    }
  }

  // Update methods
  private void updateProject(Project project) {
    projects.clear();
    projects.add(project);
  }

  private void updateSprint(Sprint sprint) {
    sprints.clear();
    sprints.add(sprint);
  }

  private void updateTask(Task task) {
    tasks.clear();
    tasks.add(task);
  }

  private void updateProjects(List<Project> newProjects) {
    projects.clear();
    projects.addAll(newProjects);
  }

  private void updateSprints(List<Sprint> newSprints) {
    sprints.clear();
    sprints.addAll(newSprints);
  }

  private void updateTasks(List<Task> newTasks) {
    tasks.clear();
    tasks.addAll(newTasks);
  }
}