package Network.RequestHandling;

import ClientModel.Requests.ProjectRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class EndProjectHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        ProjectRequest projectRequest = (ProjectRequest) request;
        if(modelManager.endProject(projectRequest.getProject()))
        {
            //broadcast here
            modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(projectRequest.getProject().getProject_id()));
        }
    }
}
