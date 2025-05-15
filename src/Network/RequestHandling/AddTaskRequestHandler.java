package Network.RequestHandling;

import ClientModel.Requests.AddTaskRequest;
import ClientModel.Requests.Request;
import Model.Project;
import Network.ServerModelManager;

public class AddTaskRequestHandler implements RequestHandlerStrategy{

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        AddTaskRequest addTaskRequest = (AddTaskRequest) request;
        Project project = modelManager.getProjects().get(addTaskRequest.getSprint().getProject_id());
        if(modelManager.addTask(addTaskRequest.getSprint(), project, addTaskRequest.getName(), addTaskRequest.getDescription(), addTaskRequest.getPriority()))
        {
            // broadcast
            modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(addTaskRequest.getSprint().getProject_id()));
        }
    }
}
