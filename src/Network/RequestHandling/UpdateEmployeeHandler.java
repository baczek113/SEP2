package Network.RequestHandling;

import ClientModel.Requests.EmployeeRequest;
import ClientModel.Requests.Request;
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
