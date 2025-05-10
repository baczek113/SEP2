package Model;

public class Role {
    private int role_id;
    private String role_name;

    public Role(int role_id) {
        this.role_id = role_id;
        switch (role_id)
        {
            case 1:
                this.role_name = "admin";
                break;
            case 2:
                this.role_name = "product_owner";
                break;
            case 3:
                this.role_name = "scrum_master";
                break;
            case 4:
                this.role_name = "developer";
                break;
            default:
                this.role_name = "incorrect_role";
                break;
        }
    }

    public String getRole_name() {
        return role_name;
    }
    public int getRole_id() {
        return role_id;
    }
}
