package Network;

import ClientModel.Requests.LoginRequest;
import ClientModel.Requests.Request;
import DataModel.*;
import Network.Database.DAO;
import Network.Database.Initializer;
import Network.RequestHandling.*;
import Network.Response.LoginResponse;
import Network.Response.Response;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerModelManager {
    private static ServerModelManager instance;
    private List<Project> projects;
    private List<Employee> employees;
    private DAO dao;
    private RequestHandlerStrategy requestHandler;
    
    //synchronization, writers priority
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true); 
    private final Object writerLock = new Object();
    private int waitingWriters = 0;
    private ConnectionPool connectionPool;

    private ServerModelManager() {
        try {
            Initializer initializer = Initializer.getInstance();
            this.employees = initializer.getEmployees();
            this.projects = initializer.getProjects(this.employees);
            this.dao = DAO.getInstance();
            this.requestHandler = null;
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

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Project> getProjects() {
        synchronized(writerLock) {
            while (waitingWriters > 0) {
                try {
                    writerLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        }
        
        lock.readLock().lock();
        try {
            return projects;
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<Employee> getEmployees() {
        synchronized(writerLock) {
            while (waitingWriters > 0) {
                try {
                    writerLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        }

        lock.readLock().lock();
        try {
            return employees;
        } finally {
            lock.readLock().unlock();
        }
    }

    public Employee login (String username, String password)
    {
        return dao.login(username, password);
    }

    public boolean createEmployee(int role_id, String username, String password) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            Employee employee = dao.addEmployee(role_id, username, password);
            if(employee != null) {
                employees.add(employee);
                return true;
            }
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean deactivateEmployee(int employee_id) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            Employee employee = employees.get(employee_id);
            if(checkIfEmployeeTaken(employee))
                return false;
            dao.deactivateEmployee(employee);
            employee.deativate();
            return true;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean activateEmployee(int employee_id) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            Employee employee = employees.get(employee_id);
            dao.activateEmployee(employee);
            employee.activate();
            return true;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean updateEmployee(Employee employee) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            Employee proxyReflection = employees.get(employee.getEmployee_id());
            if(proxyReflection == null) {
                return false;
            }
            if(!proxyReflection.getRole().equals(employee.getRole()) && checkIfEmployeeTaken(proxyReflection)) {
                return false;
            }
            try {
                dao.editEmployee(employee);
                proxyReflection.setRole(employee.getRole());
                proxyReflection.setUsername(employee.getUsername());
                return true;
            } catch (RuntimeException e) {
                return false;
            }
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
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

    public List<Project> getProjectsForEmployee(int employee_id)
    {
        synchronized(writerLock) {
            while (waitingWriters > 0) {
                try {
                    writerLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        }

        lock.readLock().lock();
        try {
            List<Project> relevantProjects = new ProjectList();
            for (Project project : projects) {
                if(project.getEmployees().get(employee_id) != null)
                {
                    relevantProjects.add(project);
                }
            }
            return relevantProjects;
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean addProject(Employee created_by, Employee scrum_master, String name, String description, Date startDate, Date endDate, List<Employee> participants) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            List<Employee> participantsServerReflection = new EmployeeList();
            for(Employee employee : participants) {
                participantsServerReflection.add(employees.get(employee.getEmployee_id()));
            }
            Project addedProject = dao.addProject(employees.get(created_by.getEmployee_id()), 
                                                employees.get(scrum_master.getEmployee_id()), 
                                                name, description, startDate, endDate, 
                                                participantsServerReflection);
            if(addedProject != null) {
                projects.add(addedProject);
                return true;
            }
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean editProject(Project project)
    {
        synchronized(writerLock) {
            waitingWriters++;
        }
        lock.writeLock().lock();
        try {
            dao.editProject(project);
            for(Project projectReflection : projects)
            {
                if(projectReflection.getProject_id() == project.getProject_id())
                {
                    projectReflection.setName(project.getName());
                    projectReflection.setDescription(project.getDescription());
                    return true;
                }
            }
            return false;
        }
        catch (RuntimeException e)
        {
            return false;
        }
        finally
        {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean endProject(Project project) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            dao.endProject(project);
            Project projectReflection = projects.get(project.getProject_id());
            if(projectReflection != null) {
                projectReflection.setStatus("finished");
                projectReflection.setEnd_date(Date.valueOf(LocalDate.now()));
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean startProject(Project project) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            dao.startProject(project);
            Project projectReflection = projects.get(project.getProject_id());
            if(projectReflection != null) {
                projectReflection.setStatus("ongoing");
                projectReflection.setStart_date(Date.valueOf(LocalDate.now()));
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean addEmployeeToProject(Project project, Employee employee) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            Project projectReflection = projects.get(project.getProject_id());
            if(projectReflection != null) {
                dao.addEmployeeToProject(project, employee);
                projectReflection.addEmployee(employees.get(employee.getEmployee_id()));
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean removeEmployeeFromProject(Project project, Employee employee) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            Project projectReflection = projects.get(project.getProject_id());
            if(projectReflection != null) {
                dao.removeEmployeeFromProject(project, employee);
                if(projectReflection.removeEmployee(employees.get(employee.getEmployee_id()))) {
                    for(Task task : projectReflection.getBacklog())
                    {
                        task.getAssignedTo().remove(employees.get(employee.getEmployee_id()));
                    }
                    return true;
                }
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean assignPriority(Task task, int priority) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            dao.assignPriority(task, priority);
            for(Task taskReflection : projects.get(task.getProject_id()).getBacklog()) {
                if(taskReflection.getTask_id() == task.getTask_id()) {
                    taskReflection.setPriority(priority);
                    return true;
                }
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean addTask(Project project, String title, String description, int priority) throws SQLException {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            Task newTask = dao.addTask(project, title, description, priority);
            if(newTask != null) {
                Project relevantProject = projects.get(project.getProject_id());
                relevantProject.addToBacklog(newTask);
                return true;
            }
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean assignTask(Employee employee, Task task) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            for(Task taskReflection : projects.get(task.getProject_id()).getBacklog()) {
                if(taskReflection.getTask_id() == task.getTask_id()) {
                    dao.assignTask(employee, task);
                    taskReflection.assignTo(employees.get(employee.getEmployee_id()));
                    return true;
                }
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean unAssignTask(Employee employee, Task task) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            for(Task taskReflection : projects.get(task.getProject_id()).getBacklog()) {
                if(taskReflection.getTask_id() == task.getTask_id()) {
                    dao.unAssignTask(employee, task);
                    taskReflection.unassignTo(employees.get(employee.getEmployee_id()));
                    return true;
                }
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean changeTaskStatus(Task task, String status) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            dao.changeTaskStatus(task, status);
            for(Task taskReflection : projects.get(task.getProject_id()).getBacklog()) {
                if(taskReflection.getTask_id() == task.getTask_id()) {
                    taskReflection.setStatus(status);
                    return true;
                }
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean addSprint(Project project, String name, Date startDate, Date endDate) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            Sprint newSprint = dao.addSprint(project, name, startDate, endDate);
            if(newSprint != null) {
                projects.get(newSprint.getProject_id()).addSprint(newSprint);
                return true;
            }
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean addTaskToSprint(Task task, Sprint sprint) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            dao.addTaskToSprint(task, sprint);
            for(Sprint sprintReflection : projects.get(sprint.getProject_id()).getSprints()) {
                if(sprintReflection.getSprint_id() == sprint.getSprint_id()) {
                    for(Task taskReflection : projects.get(task.getProject_id()).getBacklog()) {
                        if(taskReflection.getTask_id() == task.getTask_id()) {
                            taskReflection.setSprint_id(sprint.getSprint_id());
                            sprintReflection.addTask(taskReflection);
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean removeTaskFromSprint(Task task, Sprint sprint)
    {
        synchronized(writerLock) {
            waitingWriters++;
        }

        lock.writeLock().lock();
        try
        {
            dao.removeTaskFromSprint(task, sprint);
            for(Sprint sprintReflection : projects.get(sprint.getProject_id()).getSprints())
            {
                if(sprintReflection.getSprint_id() == sprint.getSprint_id())
                {
                    for(Task taskReflection : projects.get(task.getProject_id()).getBacklog())
                    {
                        if(taskReflection.getTask_id() == task.getTask_id())
                        {
                            sprintReflection.removeTask(taskReflection);
                            taskReflection.setSprint_id(0);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean editTask(Task task)
    {
        synchronized(writerLock) {
            waitingWriters++;
        }

        lock.writeLock().lock();
        try
        {
            dao.editTask(task);
            for(Task taskReflection : projects.get(task.getProject_id()).getBacklog()) {
                if(taskReflection.getTask_id() == task.getTask_id()) {
                    taskReflection.setTitle(task.getName());
                    taskReflection.setDescription(task.getDescription());
                    return true;
                }
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean removeTask(Task task)
    {
        synchronized(writerLock) {
            waitingWriters++;
        }

        lock.writeLock().lock();
        try
        {
            dao.removeTask(task);
            for(Task taskReflection : projects.get(task.getProject_id()).getBacklog()) {
                if(taskReflection.getTask_id() == task.getTask_id()) {
                    projects.get(task.getProject_id()).getBacklog().remove(taskReflection);
                    for(Sprint sprintReflection : projects.get(task.getProject_id()).getSprints())
                    {
                        sprintReflection.removeTask(taskReflection);
                        return true;
                    }
                }
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean editSprint(Sprint sprint) {
        synchronized(writerLock) {
            waitingWriters++;
        }
        
        lock.writeLock().lock();
        try {
            dao.editSprint(sprint);
            for(Sprint sprintReflection : projects.get(sprint.getProject_id()).getSprints()) {
                if(sprintReflection.getSprint_id() == sprint.getSprint_id()) {
                    sprintReflection.setName(sprint.getName());
                    sprintReflection.setStart_date(sprint.getStart_date());
                    sprintReflection.setEnd_date(sprint.getEnd_date());
                    return true;
                }
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public boolean removeSprint(Sprint sprint) {
        synchronized(writerLock) {
            waitingWriters++;
        }

        lock.writeLock().lock();
        try {
            dao.removeSprint(sprint);
            Iterator<Sprint> employeeIterator = projects.get(sprint.getProject_id()).getSprints().iterator();
            while(employeeIterator.hasNext())
            {
                Sprint sprintReflection = employeeIterator.next();
                if(sprintReflection.getSprint_id() == sprint.getSprint_id()) {
                    for(Task taskReflection : sprintReflection.getTasks()) {
                        taskReflection.setSprint_id(0);
                    }
                    employeeIterator.remove();
                    return true;
                }
            }
            return false;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        } finally {
            lock.writeLock().unlock();
            synchronized(writerLock) {
                waitingWriters--;
                writerLock.notifyAll();
            }
        }
    }

    public void processRequest(Request request) throws SQLException {
        switch(request.getAction()){
            case "createEmployee":
                requestHandler = new CreateEmployeeHandler();
                break;
            case "deactivateEmployee":
                requestHandler = new DeactivateEmployeeHandler();
                break;
            case "activateEmployee":
                requestHandler = new ActivateEmployeeHandler();
                break;
            case "updateEmployee":
                requestHandler = new UpdateEmployeeHandler();
                break;
            case "addProject":
                requestHandler = new AddProjectHandler();
                break;
            case "endProject":
                requestHandler = new EndProjectHandler();
                break;
            case "startProject":
                requestHandler = new StartProjectHandler();
                break;
            case "addEmployeeToProject":
                requestHandler = new AddEmployeeToProjectHandler();
                break;
            case "removeEmployeeFromProject":
                requestHandler = new RemoveEmployeeFromProjectHandler();
                break;
            case "assignTaskPriority":
                requestHandler = new AssignPriorityHandler();
                break;
            case "addTask":
                requestHandler = new AddTaskRequestHandler();
                break;
            case "assignTask":
                requestHandler = new AssignTaskHandler();
                break;
            case "unassignTask":
                requestHandler = new UnassignTaskHandler();
                break;
            case "changeTaskStatus":
                requestHandler = new ChangeTaskStatusHandler();
                break;
            case "addSprint":
                requestHandler = new AddSprintHandler();
                break;
            case "addTaskToSprint":
                requestHandler = new AddTaskToSprintHandler();
                break;
            case "removeTaskFromSprint":
                requestHandler = new RemoveTaskFromSprintHandler();
                break;
            case "editTask":
                requestHandler = new EditTaskHandler();
                break;
            case "removeTask":
                requestHandler = new RemoveTaskHandler();
                break;
            case "editSprint":
                requestHandler = new EditSprintHandler();
                break;
            case "editProject":
                requestHandler = new EditProjectHandler();
                break;
            case "removeSprint":
                requestHandler = new RemoveSprintHandler();
                break;
            default:
                requestHandler = null;
                break;
        }
        if(requestHandler != null) {
            requestHandler.processRequest(request, this);
        }
        else
        {
            System.out.println("No handler found for action: " + request.getAction());
        }
    }

    public Response processLogin(Request request)
    {
        LoginRequest loginRequest = (LoginRequest) request;
        Employee loggedEmployee = this.login(loginRequest.getUsername(), loginRequest.getPassword());
        if(loggedEmployee != null && loggedEmployee.getStatus().equals("active")) {
            return new LoginResponse("loginSuccess", loggedEmployee);
        }
        return new LoginResponse("loginFailure", null);
    }
}
