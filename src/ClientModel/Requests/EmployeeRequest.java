package ClientModel.Requests;

import Model.Employee;

public class EmployeeRequest extends Request {
    private Employee employeeToSend;

    public EmployeeRequest(String action, Employee employee, Employee employeeToSend) {
        super(action, employee);
        this.employeeToSend = employeeToSend;
    }

    public Employee getEmployeeToSend() {
        return employeeToSend;
    }
}
