package Network.RequestHandling;

import ClientModel.ServerInteractions.ProjectRequest;
import ClientModel.ServerInteractions.Request;
import Network.ServerModelManager;

public class EndProjectHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        ProjectRequest projectRequest = (ProjectRequest) request;
        if(modelManager.endProject(projectRequest.getProject()))
        {
            //broadcast here
        }
    }
}
