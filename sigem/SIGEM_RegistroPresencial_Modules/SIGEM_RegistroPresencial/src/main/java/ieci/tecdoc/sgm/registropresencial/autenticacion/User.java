package ieci.tecdoc.sgm.registropresencial.autenticacion;

import java.util.Locale;

public class User {

	private String userName = null;

	private String password = null;

	private Locale locale = null;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("userName [");
		buffer.append(userName);
		buffer.append("]");
		return buffer.toString();
	}
}
