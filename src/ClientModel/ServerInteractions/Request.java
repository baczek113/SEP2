package ClientModel.ServerInteractions;

import Model.Employee;

import java.io.Serializable;

public class Request implements Serializable
{
    private String action;
    private Employee employee;

    public Request(String action, Employee employee)
    {
        this.action = action;
        this.employee = employee;
    }

    public String getAction()
    {
        return action;
    }
    public Employee getEmployee()
    {
        return employee;
    }
}
