package Network.RequestHandling;

import ClientModel.Requests.AssignTaskRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class AssignTaskHandler implements RequestHandlerStrategy{

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        AssignTaskRequest assignTaskRequest = (AssignTaskRequest) request;
        if(modelManager.assignTask(assignTaskRequest.getEmployee(), assignTaskRequest.getTask())){
            // broadcast
        }
    }
}
