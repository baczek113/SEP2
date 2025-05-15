package Network.RequestHandling;

import ClientModel.Requests.AssignTaskRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class UnassignTaskHandler implements RequestHandlerStrategy{

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        AssignTaskRequest assignTaskRequest = (AssignTaskRequest) request;
        if(modelManager.unAssignTask(assignTaskRequest.getEmployee(), assignTaskRequest.getTask())){
            // broadcast
            modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(assignTaskRequest.getTask().getProject_id()));
        }
    }
}
