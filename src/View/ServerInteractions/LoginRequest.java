package View.ServerInteractions;

public class LoginRequest extends Request
{

  private String username, password;

  public LoginRequest(String action, String username, String password)
  {
    super(action);
    this.username = username;
    this.password = password;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

}
