package Network;

import ClientModel.ServerInteractions.Request;
import Model.Employee;
import Model.Project;
import Network.Database.DAO;
import Network.Database.Initializer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerModelManager {
    private static ServerModelManager instance;
    private List<Project> projects;
    private List<Employee> employees;
    private DAO dao;

    private ServerModelManager() {
        try {
            Initializer initializer = Initializer.getInstance();
            this.employees = initializer.getEmployees();
            this.projects = initializer.getProjects(this.employees);
            this.dao = DAO.getInstance();
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

    public Employee createEmployee(int role_id, String username, String password)
    {
        Employee employee = dao.addEmployee(role_id, username, password);
        employees.add(employee);
        return employee;
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

    private List<Project> getProjects(int employee_id)
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


















    public Response processRequest(Request request) {
        return RequestHandler.getInstance().processRequest(request);
    }
}
