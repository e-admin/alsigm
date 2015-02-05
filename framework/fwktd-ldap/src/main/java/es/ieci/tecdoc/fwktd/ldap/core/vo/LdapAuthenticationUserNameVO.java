package es.ieci.tecdoc.fwktd.ldap.core.vo;

/**
 * VO para autenticar en el manager LDAP
 * @author Iecisa
 * @version $Revision: 79 $
 *
 */
public class LdapAuthenticationUserNameVO extends LdapAuthenticationInfoVO {

	/**
	 * Nombre del usuario a autenticar
	 */
	private String userName;

	/**
	 * Obtiene el nombre del usuario a autenticar
	 * @return Nombre del usuario a autenticar
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Establece el nombre del usuario a autenticar
	 * @param userName nombre del usuario a autenticar
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationInfoVO#getUserIdentification()
	 */
	public String getUserIdentification() {
		return userName;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new StringBuffer()
		.append("(userName:")
		.append(userName)
		.append(",userCredentials:")
		.append(getUserCredentials())
		.append(")").toString();
	}

}