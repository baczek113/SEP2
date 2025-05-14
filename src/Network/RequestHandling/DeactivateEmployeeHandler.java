package Network.RequestHandling;

import ClientModel.ServerInteractions.EmployeeRequest;
import ClientModel.ServerInteractions.Request;
import Network.ServerModelManager;

public class DeactivateEmployeeHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        if(modelManager.deactivateEmployee(((EmployeeRequest) request).getEmployeeToSend().getEmployee_id()))
        {
            // broadcast stuff 1
        }
    }
}
