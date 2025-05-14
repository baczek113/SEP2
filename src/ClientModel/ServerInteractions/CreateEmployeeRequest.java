package ClientModel.ServerInteractions;

import Model.Employee;

public class CreateEmployeeRequest extends LoginRequest
{
    private int role_id;
    public CreateEmployeeRequest(String action, Employee employee, String username, String password, int role_id)
    {
        super("createEmployee", employee, username, password);
        this.role_id = role_id;
    }
    public int getRole_id()
    {
        return role_id;
    }
}