package Network.RequestHandling;

import ClientModel.Requests.Request;
import ClientModel.Requests.RemoveSprintRequest;
import Network.ServerModelManager;

public class RemoveSprintHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        RemoveSprintRequest removeSprintRequest = (RemoveSprintRequest) request;
        if(modelManager.removeSprint(removeSprintRequest.getSprint())){
            // Broadcast
            modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(removeSprintRequest.getSprint().getProject_id()));
        }
    }
}
