package ClientModel.Requests;

import Model.Employee;
import Model.Task;

public class EditTaskRequest extends Request {
    private Task task;

    public EditTaskRequest(String action, Employee employee, Task task) {
        super(action, employee);
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
