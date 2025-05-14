package Network.RequestHandling;

import ClientModel.Requests.EditTaskRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class EditTaskHandler implements RequestHandlerStrategy{

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        EditTaskRequest editTaskRequest = (EditTaskRequest) request;
        if(modelManager.editTask(editTaskRequest.getTask()))
        {
            //broadcast
        }
    }
}
