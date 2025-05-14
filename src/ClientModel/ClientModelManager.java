package ClientModel;

import ClientModel.Requests.*;
import Model.*;
import Network.Response.EmployeeResponse;
import Network.Response.LoginResponse;
import Network.Response.ProjectResponse;
import Network.Response.Response;

import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.util.List;

public class ClientModelManager {
    private PropertyChangeSupport propertyChangeSupport;
    private List<Employee> employees;
    private List<Project> projects;
    private Employee loggedEmployee;
    private ClientConnection client;

    public ClientModelManager()
    {
        propertyChangeSupport = new PropertyChangeSupport(this);
        employees = new EmployeeList();
        projects = new ProjectList();
        loggedEmployee = null;
    }

    public void setConnection(ClientConnection clientConnection){
        this.client = clientConnection;
    }

    public void handleServerResponse(Response response)
    {
        String message = response.getMessage();

        switch (message)
        {
            case "login":
                LoginResponse loginResponse = (LoginResponse) response;
                if (loginResponse.getEmployee() == null)
                {
                    propertyChangeSupport.firePropertyChange("loginFailed", null, null);
                }
                else
                {
                    propertyChangeSupport.firePropertyChange("loginSuccessful", null, loginResponse.getEmployee());
                }
                break;
            case "project":
                ProjectResponse projectResponse = (ProjectResponse) response;
                projects = projectResponse.getProjects();
                propertyChangeSupport.firePropertyChange("projects", null, projects);
                break;
            case "employee":
                EmployeeResponse employeeResponse = (EmployeeResponse) response;
                employees = employeeResponse.getEmployees();
                propertyChangeSupport.firePropertyChange("employees", null, employees);
        }
    }

//    public void addSprint(Project project, String name, Date startDate, Date endDate)
//    {
//        Request request = new AddSprintRequest("addSprint", loggedEmployee, project, name, startDate, endDate);
//        client.sendRequest(request);
//    }
//
//    public void addTask(Sprint sprint, String name, String description, int priority)
//    {
//        Request request = new AddTaskRequest("addTask", loggedEmployee, sprint, name, description, priority);
//        client.sendRequest(request);
//    }
//
//    public void reloadTasks(Sprint sprint)
//    {
//        Request request = new SprintRequest("getTasks", loggedEmployee, sprint);
//        client.sendRequest(request);
//        //Send request and update ArrayList<> :PPP
//    }
//
//    public void reloadProjects()
//    {
//        Request request = new Request("getProjects", loggedEmployee);
//        client.sendRequest(request);
//        //Send request and update ArrayList<> :PPP
//    }
//
//    public void reloadSprints(Project project)
//    {
//        Request request = new ProjectRequest("getSprints", loggedEmployee, project);
//        client.sendRequest(request);
//        //Send request and update ArrayList<> :PPP
//    }
//
//    public void login(String username, String password){
//        Request request = new LoginRequest("login", loggedEmployee, username, password);
//        client.sendRequest(request);
//    }
//
//    public void assignTask(Employee employee, Task task){
//        Request request = new AssignTaskRequest("assignTask", employee, task);
//        client.sendRequest(request);
//    }
//
//    public void assignProject(Employee employee, Project project, Employee employeeToAssign){
//        Request request = new AssignProject("assignProject", employee, project, employeeToAssign);
//        client.sendRequest(request);
//    }
//    // Add create user method
}
