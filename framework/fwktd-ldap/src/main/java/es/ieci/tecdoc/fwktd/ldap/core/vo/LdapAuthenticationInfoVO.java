package es.ieci.tecdoc.fwktd.ldap.core.vo;

/**
 * Informacion de autenticacion
 * @author Iecisa
 * @version $Revision: 79 $
 *
 */
public abstract class LdapAuthenticationInfoVO {

	/**
	 * Password del usuario a autenticar
	 */
	private String userCredentials;

	/**
	 * Obtiene la password del usuario a autenticar
	 * @return Password del usuario a autenticar
	 */
	public String getUserCredentials() {
		return userCredentials;
	}

	/**
	 * Establece la password del usuario a autenticar
	 * @param userCredentials Password del usuario a autenticar
	 */
	public void setUserCredentials(final String userCredentials) {
		this.userCredentials = userCredentials;
	}

	/**
	 * Devuelve la identificacion del usuario
	 * @return identificacion del usuario
	 */
	public abstract String getUserIdentification();

}
