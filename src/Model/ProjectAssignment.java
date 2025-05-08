package Model;

public class ProjectAssignment {
    private int employee_id;
    private int project_id;

    public ProjectAssignment(int employee_id, int project_id) {
        this.employee_id = employee_id;
        this.project_id = project_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }
}
