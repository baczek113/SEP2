package Network;

import DataModel.Employee;
import DataModel.Project;
import DataModel.ProjectList;
import Network.Response.*;

import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    ArrayList<ServerConnection> connections;
    ServerModelManager serverModelManager;

    public ConnectionPool(ServerModelManager modelManager) {
        connections = new ArrayList<>();
        this.serverModelManager = modelManager;
    }

    public synchronized void addConnection(ServerConnection connection)
    {
        connections.add(connection);
    }

    public void broadcastProjects(List<Employee> affectedEmployees)
    {
        for(ServerConnection connection : connections)
        {
            if(affectedEmployees.get(connection.getEmployee().getEmployee_id()) != null)
            {
                try {
                    connection.sendResponse(new ProjectResponse(serverModelManager.getProjectsForEmployee(connection.getEmployee().getEmployee_id())));
                    System.out.println("Successfully broadcasted projects");
                }
                catch (Exception e)
                {
                    System.out.println("Failed to broadcast projects");
                }
            }
        }
    }

    public void broadcastEmployees()
    {
        for(ServerConnection connection : connections)
        {
            if(connection.getEmployee().getRole().getRole_name().equals("product_owner") ||
                    connection.getEmployee().getRole().getRole_name().equals("admin"))
            {
                try {
                    connection.sendResponse(new EmployeeResponse(serverModelManager.getEmployees()));
                    System.out.println("Successfully broadcasted employees");
                }
                catch (Exception e) {
                    System.out.println("Failed to broadcast employees");
                }
            }
        }
    }

    public void broadcastProject(Project project)
    {
        for(ServerConnection connection : connections)
        {
            if(project.getEmployees().get(connection.getEmployee().getEmployee_id()) != null)
            {
                try {
                    List<Project> projectList = new ProjectList();
                    projectList.add(project);
                    connection.sendResponse(new ProjectResponse(projectList));
                    System.out.println("Successfully broadcasted project");
                }
                catch (Exception e)
                {
                    System.out.println("Failed to broadcast project");
                }
            }
        }
    }

    public void sendErrorToSingleEmployee(Employee employee, String errorMessage) {
        for(ServerConnection connection : connections)
        {
            if(connection.getEmployee().getEmployee_id() == employee.getEmployee_id())
            {
                try {
                    connection.sendResponse(new ErrorResponse(errorMessage));
                }
                catch (Exception e) {
                    System.out.println("Failed to send error message");
                }
            }
        }
    }

    public synchronized void removeConnection(ServerConnection connection)
    {
        connections.remove(connection);
    }
}
