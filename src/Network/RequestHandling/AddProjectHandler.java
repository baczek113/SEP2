package Network.RequestHandling;

import ClientModel.ServerInteractions.AddProjectRequest;
import ClientModel.ServerInteractions.Request;
import Network.ServerModelManager;

public class AddProjectHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        AddProjectRequest addProjectRequest = (AddProjectRequest) request;
        if(modelManager.addProject(addProjectRequest.getEmployee(),
                addProjectRequest.getScrum_master(),
                addProjectRequest.getName(),
                addProjectRequest.getDescription(),
                addProjectRequest.getStart_date(),
                addProjectRequest.getEnd_date(),
                addProjectRequest.getAssignees()))
        {
            //broadcast stuff
        }
    }
}
