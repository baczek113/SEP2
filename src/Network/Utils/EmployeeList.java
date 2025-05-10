package Network.Utils;

import Model.Employee;

import java.util.ArrayList;

public class EmployeeList {
    private ArrayList<Employee> employees;

    public EmployeeList() {
        employees = new ArrayList<Employee>();
    }

    public void add(Employee employee) {
        employees.add(employee);
    }

    public void remove(Employee employee) {
        employees.remove(employee);
    }

    public Employee getEmployeeById(int id)
    {
        for(Employee employee : employees)
        {
            if(employee.getEmployee_id() == id)
            {
                return employee;
            }
        }

        return null;
    }

    public ArrayList<Employee> getArrayList()
    {
        return employees;
    }
}
