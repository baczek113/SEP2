package Network.RequestHandling;

import ClientModel.Requests.AddTaskRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class AddTaskRequestHandler implements RequestHandlerStrategy{

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        AddTaskRequest addTaskRequest = (AddTaskRequest) request;
        if(modelManager.addTask(addTaskRequest.getSprint(), modelManager.getProjects().get(addTaskRequest.getSprint().getProject_id()), addTaskRequest.getName(), addTaskRequest.getDescription(), addTaskRequest.getPriority()))
        {
            // broadcast
        }
    }
}
