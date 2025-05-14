package Network.RequestHandling;

import ClientModel.Requests.ChangeTaskStatusRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class ChangeTaskStatusHandler implements RequestHandlerStrategy{

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        ChangeTaskStatusRequest changeTaskStatusRequest = (ChangeTaskStatusRequest) request;
        if(modelManager.changeTaskStatus(changeTaskStatusRequest.getTask(), changeTaskStatusRequest.getStatus())){
            // broadcast
        }
    }
}
