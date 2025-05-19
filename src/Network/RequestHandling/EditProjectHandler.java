package Network.RequestHandling;

import ClientModel.Requests.EditSprintRequest;
import ClientModel.Requests.ProjectRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class EditProjectHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        ProjectRequest editSprintRequest = (ProjectRequest) request;
        if(modelManager.editProject(editSprintRequest.getProject()))
        {
            // broadcast
            modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(editSprintRequest.getProject().getProject_id()));
        }
    }
}
