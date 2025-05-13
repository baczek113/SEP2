package Network;

import Model.Employee;
import Model.Project;

public class ModelManagerTest {
    public static void main(String[] args) {
        ServerModelManager manager = ServerModelManager.getInstance();

        manager.activateEmployee(4);

        for(Employee employee : manager.getEmployees())
        {
            System.out.println("Username: " + employee.getUsername());
            System.out.println("Status: " + employee.getStatus() + "\n");
        }

        for(Project project : manager.getProjects())
        {
            for(Employee employee : project.getEmployees())
            {
                System.out.println("Username: " + employee.getUsername());
                System.out.println("Status: " + employee.getStatus() + "\n");
            }
        }
    }
}
