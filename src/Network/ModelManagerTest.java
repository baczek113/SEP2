package Network;

import Model.Employee;
import Model.EmployeeList;
import Model.Project;

import java.util.List;

public class ModelManagerTest {
    public static void main(String[] args) {
        ServerModelManager manager = ServerModelManager.getInstance();

        manager.addEmployeeToProject(manager.getProjects().get(4), manager.getEmployees().get(5));

        for(Project project : manager.getProjects())
        {
            if(project.getProject_id() == 4)
            {
                if (manager.removeEmployeeFromProject(project, manager.getEmployees().get(5)))
                {
                    System.out.println("Employee removed from project");
                    for(Employee employee : project.getEmployees())
                    {
                        System.out.println(employee.getEmployee_id());
                    }
                }
            }
        }

    }
}
