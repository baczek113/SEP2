package Network.RequestHandling;

import ClientModel.Requests.TaskSprintRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class RemoveTaskFromSprintHandler implements RequestHandlerStrategy{

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        TaskSprintRequest taskSprintRequest = (TaskSprintRequest) request;
        if(modelManager.removeTaskFromSprint(taskSprintRequest.getTask(), taskSprintRequest.getSprint())){
            // broadcast
        }
    }
}
