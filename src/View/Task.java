package View;

public class Task {
    private String name;
    private String createdBy;
    private String priority;
    private String status;

    public Task(String name, String createdBy, String priority, String status) {
        this.name = name;
        this.createdBy = createdBy;
        this.priority = priority;
        this.status = status;
    }

    public String getName() { return name; }
    public String getCreatedBy() { return createdBy; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }

    // Setters if you want to edit later
}
