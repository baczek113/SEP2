package Network.RequestHandling;

import ClientModel.Requests.AddTaskRequest;
import ClientModel.Requests.Request;
import Model.Project;
import Model.Sprint;
import Network.ServerModelManager;

import java.sql.SQLException;

public class AddTaskRequestHandler implements RequestHandlerStrategy {

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) throws SQLException {
        AddTaskRequest addTaskRequest = (AddTaskRequest) request;
        Project project = addTaskRequest.getProject();

        if (project == null) {
            System.err.println("AddTaskRequest failed");
            return;
        }

        if (modelManager.addTask(project, addTaskRequest.getName(), addTaskRequest.getDescription(), addTaskRequest.getPriority()))
        {
          modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(project.getProject_id()));
        }
    }
}