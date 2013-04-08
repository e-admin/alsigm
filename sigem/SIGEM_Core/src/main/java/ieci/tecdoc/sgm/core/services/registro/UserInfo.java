package ieci.tecdoc.sgm.core.services.registro;

import java.util.Locale;

/**
 * Datos del usuario. Login. Password y Locale
 * 
 * 
 */
public class UserInfo {

	protected String userName = null;

	protected String password = null;

	protected Locale locale = null;

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("userName [");
		buffer.append(userName);
		buffer.append("]");
		return buffer.toString();
	}
}
