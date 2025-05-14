package Network.RequestHandling;

import ClientModel.Requests.CreateEmployeeRequest;
import ClientModel.Requests.Request;
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
