package Network.RequestHandling;

import ClientModel.Requests.ProjectRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class StartProjectHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        ProjectRequest projectRequest = (ProjectRequest) request;
        if(modelManager.startProject(projectRequest.getProject()))
        {
            //broadcast here
            modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(projectRequest.getProject().getProject_id()));
        }
    }
}
