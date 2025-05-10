package Network;

import Model.Employee;
import Model.Project;

import java.util.ArrayList;
import java.util.List;

public class ServerModelManager {
    private static ServerModelManager instance;
    private List<Project> projects;
    private List<Employee> employees;

    public ServerModelManager() {

    }

    public static ServerModelManager getInstance() {
        if (instance == null) {
            instance = new ServerModelManager();
        }
        return instance;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
