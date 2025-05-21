package ClientModel.Requests;

import DataModel.Employee;
import DataModel.Task;

public class ChangeTaskStatusRequest extends Request{
    private Task task;
    private String status;
    public ChangeTaskStatusRequest(Employee employee, Task task, String status) {
        super("changeTaskStatus", employee);
        this.task = task;
        this.status = status;
    }

    public Task getTask() {
        return task;
    }

    public String getStatus() {
        return status;
    }
}
