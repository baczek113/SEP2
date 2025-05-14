package Network.RequestHandling;

import ClientModel.Requests.EmployeeRequest;
import ClientModel.Requests.Request;
import Network.ServerModelManager;

public class ActivateEmployeeHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        if(modelManager.activateEmployee(((EmployeeRequest) request).getEmployeeToSend().getEmployee_id()))
        {
            // broadcast stuff
        }
    }
}
