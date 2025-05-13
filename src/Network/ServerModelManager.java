package Network;

import Model.Project;
import Network.Database.Initializer;
import Model.EmployeeList;

import java.sql.SQLException;
import java.util.ArrayList;

public class ServerModelManager {
    private static ServerModelManager instance;
    private ArrayList<Project> projects;
    private EmployeeList employees;

    public ServerModelManager() {
        try {
            Initializer initializer = Initializer.getInstance();
            this.employees = initializer.getEmployees();
            this.projects = initializer.getProjects(this.employees);
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

    public ArrayList<Project> setProjects() {
        return projects;
    }

    public EmployeeList setEmployees() {
        return employees;
    }
}
