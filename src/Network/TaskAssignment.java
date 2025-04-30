package Network;

public class TaskAssignment {
    private int employee_id;
    private int task_id;

    public TaskAssignment(int employee_id, int task_id) {
        this.employee_id = employee_id;
        this.task_id = task_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
}
