package Network.RequestHandling;

import ClientModel.Requests.ProjectEmployeeRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class AddEmployeeToProjectHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        ProjectEmployeeRequest projectEmployeeRequest = (ProjectEmployeeRequest) request;
        if(modelManager.addEmployeeToProject(projectEmployeeRequest.getProject(), projectEmployeeRequest.getEmployeeToAdd())){
            // Broadcast
        }
    }
}
