package Network;

import Model.Employee;
import Model.Project;
import Network.Utils.EmployeeList;
import java.util.ArrayList;

public class ServerModelManager {
    private static ServerModelManager instance;
    private ArrayList<Project> projects;
    private EmployeeList employees;

    public ServerModelManager() {

    }

    public static ServerModelManager getInstance() {
        if (instance == null) {
            instance = new ServerModelManager();
        }
        return instance;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public void setEmployees(EmployeeList employees) {
        this.employees = employees;
    }
}
