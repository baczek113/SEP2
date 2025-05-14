package Network.RequestHandling;

import ClientModel.ServerInteractions.EmployeeRequest;
import ClientModel.ServerInteractions.Request;
import Network.ServerModelManager;

public class UpdateEmployeeHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        EmployeeRequest employeeRequest = (EmployeeRequest) request;
        if(modelManager.updateEmployee(employeeRequest.getEmployeeToSend()))
        {
            // broadcasting
        }
    }
}
