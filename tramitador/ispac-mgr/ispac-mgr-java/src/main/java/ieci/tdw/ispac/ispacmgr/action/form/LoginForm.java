package ieci.tdw.ispac.ispacmgr.action.form;
import org.apache.struts.validator.ValidatorForm;

public class LoginForm extends ValidatorForm  {
	
	private static final long serialVersionUID = 1L;
	
  private String user;
  private String password;
  //private boolean enterAnyWay;
  private boolean forceExclussion;

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

public boolean isForceExclussion() {
	return forceExclussion;
}

public void setForceExclussion(boolean forceExclussion) {
	this.forceExclussion = forceExclussion;
}

//	public boolean isEnterAnyWay() {
//		return enterAnyWay;
//	}
//
//	public void setEnterAnyWay(boolean enterAnyWay) {
//		this.enterAnyWay = enterAnyWay;
//	}
}