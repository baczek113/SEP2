package Network.RequestHandling;

import ClientModel.Requests.EmployeeRequest;
import ClientModel.Requests.Request;
import DataModel.Employee;
import DataModel.EmployeeList;
import DataModel.Project;
import Network.ServerModelManager;

import java.util.List;

public class DeactivateEmployeeHandler implements RequestHandlerStrategy{
    @Override
    public void processRequest(Request request, ServerModelManager modelManager) {
        EmployeeRequest employeeRequest = (EmployeeRequest) request;
        if(modelManager.deactivateEmployee(employeeRequest.getEmployeeToSend().getEmployee_id()))
        {
            // broadcast stuff
            modelManager.getConnectionPool().broadcastEmployees();
            List<Employee> broadcastProjectsTo = new EmployeeList();
            for(Project project : modelManager.getProjects())
            {
                if(project.getEmployees().get(employeeRequest.getEmployeeToSend().getEmployee_id()) != null)
                {
                    for(Employee employee : project.getEmployees())
                    {
                        broadcastProjectsTo.add(employee);
                    }
                }
            }
            modelManager.getConnectionPool().broadcastProjects(broadcastProjectsTo);
        }
        else
        {
            modelManager.getConnectionPool().sendErrorToSingleEmployee(request.getEmployee(), "cannotDeactivateEmployee");
        }
    }
}
