package ClientModel.Requests;

import DataModel.Employee;
import DataModel.Sprint;

public class EditSprintRequest extends Request{
    private Sprint sprint;
    public EditSprintRequest(Employee employee, Sprint sprint) {
        super("editSprint", employee);
        this.sprint = sprint;
    }

    public Sprint getSprint() {
        return sprint;
    }
}
