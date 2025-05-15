package Network.RequestHandling;

import ClientModel.Requests.AddSprintRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class AddSprintHandler implements RequestHandlerStrategy{

    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        AddSprintRequest addSprintRequest = (AddSprintRequest) request;
        if(modelManager.addSprint(addSprintRequest.getProject(), addSprintRequest.getName(), addSprintRequest.getStartDate(), addSprintRequest.getEndDate()))
        {
            // broadcast stuff
            modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(addSprintRequest.getProject().getProject_id()));
        }
    }
}
