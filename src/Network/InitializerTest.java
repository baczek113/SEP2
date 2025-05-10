package Network;

import Model.Employee;
import Model.Project;
import Model.Sprint;
import Model.Task;
import Network.Database.Initializer;
import Network.Utils.EmployeeList;

import java.sql.SQLException;
import java.util.ArrayList;

public class InitializerTest {
    public static void main(String[] args) throws SQLException {
        Initializer init = Initializer.getInstance();

        EmployeeList employees = init.getEmployees();
//        for(Employee employee : employees.getArrayList()) {
//            System.out.println(employee.getUsername());
//        }

        ArrayList<Project> projects = init.getProjects(employees);

        for(Project project : projects) {
            for(Sprint sprint : project.getSprints()) {
                for(Task task : sprint.getTasks())
                {
                    System.out.println(task.getCreated_by().getUsername());
                }
            }
        }
    }
}
