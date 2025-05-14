package Network.RequestHandling;

import ClientModel.Requests.AssignPriorityRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class AssignPriorityHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        AssignPriorityRequest assignPriorityRequest = (AssignPriorityRequest) request;
        if(modelManager.assignPriority(assignPriorityRequest.getTask(), assignPriorityRequest.getPriority())){
            // broadcast
        }
    }
}
