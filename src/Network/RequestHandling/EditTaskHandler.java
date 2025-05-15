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
            modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(editTaskRequest.getTask().getProject_id()));
        }
    }
}
