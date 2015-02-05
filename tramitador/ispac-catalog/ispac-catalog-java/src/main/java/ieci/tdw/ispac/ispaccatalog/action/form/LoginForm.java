package ieci.tdw.ispac.ispaccatalog.action.form;
import org.apache.struts.validator.ValidatorForm;

public class LoginForm extends ValidatorForm  {
  private String user;
  private String password;

  public String getUser() {
    return user;
  }

  public void setUser(String newUser) {
    user = newUser;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String newPassword) {
    password = newPassword;
  }
}