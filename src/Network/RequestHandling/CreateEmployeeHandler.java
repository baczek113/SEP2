package Network.RequestHandling;

import ClientModel.ServerInteractions.CreateEmployeeRequest;
import ClientModel.ServerInteractions.Request;
import Network.Response.Response;
import Network.ServerModelManager;

public class CreateEmployeeHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        CreateEmployeeRequest createEmployeeRequest = (CreateEmployeeRequest) request;
        if(modelManager.createEmployee(createEmployeeRequest.getRole_id(), createEmployeeRequest.getUsername(), createEmployeeRequest.getPassword()))
        {
            //broadcast stuff
        }
    }
}
