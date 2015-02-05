/*
 * Created on 07-abr-2005
 *
 */
package common.forms;

import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * @author LUISANVE
 * 
 */
public class LoginForm extends ArchigestActionForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String login = null;
	String password = null;
	String userType = null;
	String entity = null;
	String keySesionUsuario = null;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return Returns the userType.
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            The userType to set.
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getKeySesionUsuario() {
		return keySesionUsuario;
	}

	public void setKeySesionUsuario(String keySesionUsuario) {
		this.keySesionUsuario = keySesionUsuario;
	}
}
