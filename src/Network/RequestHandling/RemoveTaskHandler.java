package Network.RequestHandling;

import ClientModel.Requests.EditTaskRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

import java.sql.SQLException;

public class RemoveTaskHandler implements RequestHandlerStrategy{

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        EditTaskRequest editTaskRequest = (EditTaskRequest) request;
        if(modelManager.removeTask(editTaskRequest.getTask()))
        {
            //broadcast
            modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(editTaskRequest.getTask().getProject_id()));
        }
    }
}
