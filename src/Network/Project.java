package Network;

import java.sql.Date;

public class Project {
    private int project_id;
    private int created_by;
    private String name;
    private String description;
    private String status;
    private Date start_date;
    private Date end_date;

    public Project(int project_id, int created_by, String name, String description, String status, Date start_date, Date end_date) {
        this.project_id = project_id;
        this.created_by = created_by;
        this.name = name;
        this.description = description;
        this.status = status;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public int getCreated_by() {
        return created_by;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
