package ClientModel.Requests;

import Model.Employee;
import Model.Task;

public class AssignPriorityRequest extends Request{
    private Task task;
    private int priority;
    public AssignPriorityRequest(Employee employee, Task task, int priority) {
        super("assignTaskPriority", employee);
        this.task = task;
        this.priority = priority;
    }

    public Task getTask() {
        return task;
    }

    public int getPriority() {
        return priority;
    }
}
