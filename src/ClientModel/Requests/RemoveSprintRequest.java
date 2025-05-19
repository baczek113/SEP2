package ClientModel.Requests;

import Model.Employee;
import Model.Sprint;

public class RemoveSprintRequest extends Request
{
    private Sprint sprint;

    public RemoveSprintRequest(Employee employee, Sprint sprint)
    {
        super("removeSprint", employee);
        this.sprint = sprint;
    }

    public Sprint getSprint()
    {
        return sprint;
    }

}
