package View;

public class User
{
    private String username;
    private String role;
    private String status;

    public User(String username, String role, String status)
    {
        this.username = username;
        this.role = role;
        this.status= status;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }

    public void setUsername(String username)
    { this.username = username; }
    public void setRole(String role)
    { this.role = role; }
    public void setStatus (String status)
    {
        this.role=role;
    }
    public String getStatus()
    {
        return status;
    }
}
