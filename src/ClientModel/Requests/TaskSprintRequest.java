package ClientModel.Requests;

import DataModel.Employee;
import DataModel.Sprint;
import DataModel.Task;

public class TaskSprintRequest extends Request {
    private Task task;
    private Sprint sprint;

    public TaskSprintRequest(String action, Employee employee, Task task, Sprint sprint) {
        super(action, employee);
        this.task = task;
        this.sprint = sprint;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public Task getTask() {
        return task;
    }
}
