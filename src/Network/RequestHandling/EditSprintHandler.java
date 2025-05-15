package Network.RequestHandling;

import ClientModel.Requests.EditSprintRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class EditSprintHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        EditSprintRequest editSprintRequest = (EditSprintRequest) request;
        if(modelManager.editSprint(editSprintRequest.getSprint()))
        {
            // broadcast
            modelManager.getConnectionPool().broadcastProject(modelManager.getProjects().get(editSprintRequest.getSprint().getProject_id()));
        }
    }
}
