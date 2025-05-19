package ClientModel;

import ClientModel.Requests.*;
import Model.*;
import Network.Response.EmployeeResponse;
import Network.Response.LoginResponse;
import Network.Response.ProjectResponse;
import Network.Response.Response;
import View.EditProjectViewController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.util.List;

public class ClientModelManager {
    private PropertyChangeSupport propertyChangeSupport;
    private List<Employee> employees;
    private List<Project> projects;
    private Employee loggedEmployee;
    private ClientConnection client;
    private String host;
    private int port;

    public ClientModelManager(String host, int port)
    {
        propertyChangeSupport = new PropertyChangeSupport(this);
        employees = new EmployeeList();
        projects = new ProjectList();
        loggedEmployee = null;
        this.host = host;
        this.port = port;
    }

    public void addListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void addListener(String name, PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(name, listener);
    }

    public synchronized void handleServerResponse(Response response)
    {
        String message = response.getMessage();

        switch (message)
        {
            case "loginSuccess":
                System.out.println("Login successful");
                loggedEmployee = ((LoginResponse) response).getEmployee();
                propertyChangeSupport.firePropertyChange("login", null, loggedEmployee);
                break;
            case "loginFailure":
                System.out.println("Login failed");
                client = null;
                propertyChangeSupport.firePropertyChange("login", null, null);
                break;
            case "project":
                System.out.println("Project response received");
                ProjectResponse projectResponse = (ProjectResponse) response;
                for(Project project : projectResponse.getProjects())
                {
                    if(projects.get(project.getProject_id()) == null)
                    {
                        projects.add(project);
                    }
                    else
                    {
                        projects.set(project.getProject_id(), project);
                    }
                }
                propertyChangeSupport.firePropertyChange("projects", null, projects);
                break;
            case "employee":
                System.out.println("Employee response received");
                EmployeeResponse employeeResponse = (EmployeeResponse) response;
                employees = employeeResponse.getEmployees();
                propertyChangeSupport.firePropertyChange("employees", null, employees);
                break;
        }
    }

    public synchronized void login(String username, String password){
        if(loggedEmployee == null) {
            LoginRequest loginRequest = new LoginRequest("login", null, username, password);
            client = new ClientConnection(host, port, this, loginRequest);
            (new Thread(client)).start();
        }
    }

    public void createEmployee(String username, String password, int role_id){
        CreateEmployeeRequest createEmployee = new CreateEmployeeRequest(loggedEmployee, username, password, role_id);
        client.sendRequest(createEmployee);
    }

    public void addProject(Employee scrum_master, String name, String desc, Date start_date, Date end_date, List<Employee> assignees){
        AddProjectRequest addProject = new AddProjectRequest(loggedEmployee, scrum_master, name, desc, start_date, end_date, assignees);
        client.sendRequest(addProject);
    }

    public void addSprint(Project project, String name, Date startDate, Date endDate){
        AddSprintRequest addSprint = new AddSprintRequest(loggedEmployee, project, name, startDate, endDate);
        client.sendRequest(addSprint);
    }
    public void addTask(Project project, String name, String desc, int priority){
        AddTaskRequest addTask = new AddTaskRequest(loggedEmployee, project, name, desc, priority);
        client.sendRequest(addTask);
    }
    public void assignPriority(Task task, int priority){
        AssignPriorityRequest assignPriority = new AssignPriorityRequest(loggedEmployee, task, priority);
        client.sendRequest(assignPriority);
    }
    public void activateEmployee(Employee employee){
        EmployeeRequest activateEmployee = new EmployeeRequest("activateEmployee", loggedEmployee, employee);
        client.sendRequest(activateEmployee);
    }
    public void deactivateEmployee(Employee employee){
        EmployeeRequest deactivateEmployee = new EmployeeRequest("deactivateEmployee", loggedEmployee, employee);
        client.sendRequest(deactivateEmployee);
    }
    public void editEmployee(Employee employee){
        EmployeeRequest editEmployee = new EmployeeRequest("updateEmployee", loggedEmployee, employee);
        client.sendRequest(editEmployee);
    }
    public void assignTask(String action, Task task){
        AssignTaskRequest assignTask = new AssignTaskRequest(action, loggedEmployee, task);
        client.sendRequest(assignTask);
    }
    public void ChangeTaskStatus(Task task, String status){
        ChangeTaskStatusRequest changeStatus = new ChangeTaskStatusRequest(loggedEmployee, task, status);
        client.sendRequest(changeStatus);
    }
    public void editSprint(Sprint sprint){
        EditSprintRequest editSprint = new EditSprintRequest(loggedEmployee, sprint);
        client.sendRequest(editSprint);
    }
    public void editTask(Task task){
        EditTaskRequest editTask = new EditTaskRequest(loggedEmployee, task);
        client.sendRequest(editTask);
    }
    public void sendEmployee(String action, Employee employeeToSend){
        EmployeeRequest sendEmployee = new EmployeeRequest(action, loggedEmployee, employeeToSend);
        client.sendRequest(sendEmployee);
    }
    public void addEmployeeToProject(String action, Project project, Employee employeeToAdd){
        ProjectEmployeeRequest employeeToProject = new ProjectEmployeeRequest(action, loggedEmployee, project, employeeToAdd);
        client.sendRequest(employeeToProject);
    }
    public void sendProject(String action, Project project){
        ProjectRequest sendProject = new ProjectRequest(action, loggedEmployee, project);
        client.sendRequest(sendProject);
    }
    public void addTaskToSprint(String action, Sprint sprint, Task task){
        TaskSprintRequest addTaskToSprint = new TaskSprintRequest(action, loggedEmployee, task, sprint);
        client.sendRequest(addTaskToSprint);
    }
    public void deactivateEmployee()
    {

    }
    public Employee getLoggedEmployee(){
        return loggedEmployee;
    }

    public List<Project> getProjects()
    {
        return projects;
    }
    public List<Employee> getEmployees()
    {
        return employees;
    }
}
