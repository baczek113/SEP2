package Network;

import ClientModel.Requests.LoginRequest;
import ClientModel.Requests.Request;
import Model.*;
import Network.Database.DAO;
import Network.Database.Initializer;
import Network.RequestHandling.CreateEmployeeHandler;
import Network.RequestHandling.LoginHandler;
import Network.RequestHandling.RequestHandlerStrategy;
import Network.Response.LoginResponse;
import Network.Response.Response;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerModelManager {
    private static ServerModelManager instance;
    private List<Project> projects;
    private List<Employee> employees;
    private DAO dao;
    private RequestHandlerStrategy requestHandler;

    private ServerModelManager() {
        try {
            Initializer initializer = Initializer.getInstance();
            this.employees = initializer.getEmployees();
            this.projects = initializer.getProjects(this.employees);
            this.dao = DAO.getInstance();
            this.requestHandler = new LoginHandler();
        }
        catch (SQLException e) {
            System.out.println("ServerModelManager initialization failed");
        }
    }

    public static ServerModelManager getInstance() {
        if (instance == null) {
            instance = new ServerModelManager();
        }
        return instance;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee login (String username, String password)
    {
        return dao.login(username, password);
    }

    public boolean createEmployee(int role_id, String username, String password)
    {
        Employee employee = dao.addEmployee(role_id, username, password);
        if(employee != null) {
            employees.add(employee);
            return true;
        }
        return false;
    }

    public boolean deactivateEmployee(int employee_id)
    {
        try
        {
            Employee employee = employees.get(employee_id);
            if(checkIfEmployeeTaken(employee))
                return false;
            dao.deactivateEmployee(employee);
            employee.deativate();
            return true;
        }
        catch (RuntimeException e)
        {
            return false;
        }
    }

    public boolean activateEmployee(int employee_id)
    {
        try
        {
            Employee employee = employees.get(employee_id);
            dao.activateEmployee(employee);
            employee.activate();
            return true;
        }
        catch (RuntimeException e)
        {
            return false;
        }
    }

    public boolean updateEmployee(Employee employee)
    {
        Employee proxyReflection = employees.get(employee.getEmployee_id());
        if(proxyReflection == null)
        {
            return false;
        }
        if(!proxyReflection.getRole().equals(employee.getRole()) && checkIfEmployeeTaken(proxyReflection)) {
            return false;
        }
        try{
            dao.editEmployee(employee);
            proxyReflection.setRole(employee.getRole());
            proxyReflection.setUsername(employee.getUsername());
            return true;
        }
        catch (RuntimeException e)
        {
            return false;
        }
    }

    private boolean checkIfEmployeeTaken(Employee employee)
    {
        if (employee.getRole().getRole_name().equals("product_owner")) {
            for (Project project : projects) {
                if (project.getCreated_by().getEmployee_id() == employee.getEmployee_id()
                        && (project.getStatus().equals("ongoing") || project.getStatus().equals("pending"))) {
                    return true;
                }
            }
        }
        if (employee.getRole().getRole_name().equals("scrum_master")) {
            for (Project project : projects) {
                if (project.getScrum_master().getEmployee_id() == employee.getEmployee_id()
                        && (project.getStatus().equals("ongoing") || project.getStatus().equals("pending"))) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Project> getProjects(int employee_id)
    {
        List<Project> relevantProjects = new ArrayList<Project>();
        for (Project project : projects) {
            if(project.getEmployees().get(employee_id) != null)
            {
                relevantProjects.add(project);
            }
        }
        return relevantProjects;
    }

    public boolean addProject(Employee created_by, Employee scrum_master, String name, String description, Date startDate, Date endDate, List<Employee> participants)
    {
        List<Employee> participantsServerReflection = new EmployeeList();
        for(Employee employee : participants)
        {
            participantsServerReflection.add(employees.get(employee.getEmployee_id()));
        }
        Project addedProject = dao.addProject(employees.get(created_by.getEmployee_id()), employees.get(scrum_master.getEmployee_id()), name, description, startDate, endDate, participantsServerReflection);
        if(addedProject != null)
        {
            projects.add(addedProject);
            return true;
        }
        return false;
    }

    public boolean endProject(Project project)
    {
        try {
            dao.endProject(project);
            Project projectReflection = projects.get(project.getProject_id());
            if(projectReflection != null)
            {
                projects.remove(projectReflection);
                return true;
            }
            return false;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public boolean addEmployeeToProject(Project project, Employee employee)
    {
        try{
            Project projectReflection = projects.get(project.getProject_id());
            if(projectReflection != null)
            {
                dao.addEmployeeToProject(project, employee);
                projectReflection.addEmployee(employees.get(employee.getEmployee_id()));
                return true;
            }
            return false;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public boolean removeEmployeeFromProject(Project project, Employee employee)
    {
        try{
            Project projectReflection = projects.get(project.getProject_id());
            if(projectReflection != null)
            {
                dao.removeEmployeeFromProject(project, employee);
                projectReflection.removeEmployee(employees.get(employee.getEmployee_id()));
                return true;
            }
            return false;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public boolean assignPriority(Task task, int priority)
    {
        try
        {
            dao.assignPriority(task, priority);
            for(Task taskReflection : projects.get(task.getProject_id()).getBacklog())
            {
                if(taskReflection.getTask_id() == task.getTask_id())
                {
                    taskReflection.setPriority(priority);
                    return true;
                }
            }
            return false;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public boolean addTask(Sprint sprint, Project project, String title, String description, int priority)
    {
        Task newTask = dao.addTask(sprint, project, title, description, priority);
        if(newTask != null)
        {
            Project relevantProject = projects.get(project.getProject_id());
            relevantProject.addToBacklog(newTask);
            if(sprint != null)
            {
                for(Sprint sprintReflection : relevantProject.getSprints())
                {
                    if(sprintReflection.getSprint_id() == sprint.getSprint_id())
                    {
                        sprintReflection.addTask(newTask);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean assignTask(Employee employee, Task task)
    {
        try{
            for(Task taskReflection : projects.get(task.getProject_id()).getBacklog())
            {
                if(taskReflection.getTask_id() == task.getTask_id())
                {
                    dao.assignTask(employee, task);
                    taskReflection.assignTo(employee);
                    return true;
                }
            }
            return false;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public boolean unAssignTask(Employee employee, Task task)
    {
        try{
            for(Task taskReflection : projects.get(task.getProject_id()).getBacklog())
            {
                if(taskReflection.getTask_id() == task.getTask_id())
                {
                    dao.unAssignTask(employee, task);
                    taskReflection.unassignTo(employee);
                    return true;
                }
            }
            return false;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public boolean changeTaskStatus(Task task, String status)
    {
        try
        {
            dao.changeTaskStatus(task, status);
            for(Task taskReflection : projects.get(task.getProject_id()).getBacklog())
            {
                if(taskReflection.getTask_id() == task.getTask_id())
                {
                    taskReflection.setStatus(status);
                    return true;
                }
            }
            return false;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public boolean addSprint(Project project, String name, Date startDate, Date endDate)
    {
        Sprint newSprint = dao.addSprint(project, name, startDate, endDate);
        if(newSprint != null)
        {
            projects.get(newSprint.getProject_id()).addSprint(newSprint);
            return true;
        }
        return false;
    }

    public boolean addTaskToSprint(Task task, Sprint sprint)
    {
        try
        {
            dao.addTaskToSprint(task, sprint);
            for(Sprint sprintReflection : projects.get(sprint.getProject_id()).getSprints())
            {
                if(sprintReflection.getSprint_id() == sprint.getSprint_id())
                {
                    for(Task taskReflection : projects.get(task.getProject_id()).getBacklog())
                    {
                        if(taskReflection.getTask_id() == task.getTask_id())
                        {
                            sprintReflection.addTask(taskReflection);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public boolean editTask(Task task)
    {
        try
        {
            dao.editTask(task);
            for(Task taskReflection : projects.get(task.getProject_id()).getBacklog())
            {
                if(taskReflection.getTask_id() == task.getTask_id())
                {
                    taskReflection.setTitle(task.getTitle());
                    taskReflection.setDescription(task.getDescription());
                    return true;
                }
            }
            return false;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public boolean editSprint(Sprint sprint)
    {
        try{
            dao.editSprint(sprint);
            for(Sprint sprintReflection : projects.get(sprint.getProject_id()).getSprints())
            {
                if(sprintReflection.getSprint_id() == sprint.getSprint_id())
                {
                    sprintReflection.setName(sprint.getName());
                    sprintReflection.setStart_date(sprint.getStart_date());
                    sprintReflection.setEnd_date(sprint.getEnd_date());
                    return true;
                }
            }
            return false;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public void processRequest(Request request) {
        switch(request.getAction()){
            case "login":
                requestHandler = new LoginHandler();
                break;
            case "createEmployee":
                requestHandler = new CreateEmployeeHandler();
                break;
        }
        requestHandler.processRequest(request, this);
    }

    public Response processLogin(Request request)
    {
        LoginRequest loginRequest = (LoginRequest) request;
        Employee loggedEmployee = this.login(loginRequest.getUsername(), loginRequest.getPassword());
        if(loggedEmployee != null) {
            return new LoginResponse("loginSuccess", loggedEmployee);
        }
        return new LoginResponse("loginFailure", null);
    }
}
