package Network.RequestHandling;

import ClientModel.Requests.AddTaskRequest;
import ClientModel.Requests.Request;
import Model.Project;
import Model.Sprint;
import Network.ServerModelManager;

public class AddTaskRequestHandler implements RequestHandlerStrategy {

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        AddTaskRequest addTaskRequest = (AddTaskRequest) request;
        Sprint sprint = addTaskRequest.getSprint();
        Project project = addTaskRequest.getProject();

        if (project == null) {
            System.err.println("AddTaskRequest failed");
            return;
        }

        if (modelManager.addTask(sprint, project, addTaskRequest.getName(), addTaskRequest.getDescription(), addTaskRequest.getPriority()))
        {
          modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(sprint.getProject_id()));
        }
    }
}