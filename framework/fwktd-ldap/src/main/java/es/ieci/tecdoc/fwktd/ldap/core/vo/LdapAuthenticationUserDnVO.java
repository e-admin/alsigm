package es.ieci.tecdoc.fwktd.ldap.core.vo;

/**
 * VO para autenticar en el conector LDAP
 * @author Iecisa
 * @version $Revision: 79 $
 *
 */
public class LdapAuthenticationUserDnVO extends LdapAuthenticationInfoVO {

	/**
	 * Dn completo del usuario a autenticar
	 */
	private String userDn;

	/**
	 * Obtiene el dn completo del usuario a autenticar
	 * @return Dn completo del usuario a autenticar
	 */
	public String getUserDn() {
		return userDn;
	}

	/**
	 * Establece el dn completo del usuario a autenticar
	 * @param userDn Dn completo del usuario a autenticar
	 */
	public void setUserDn(final String userDn) {
		this.userDn = userDn;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationInfoVO#getUserIdentification()
	 */
	public String getUserIdentification() {
		return userDn;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new StringBuffer()
		.append("(userDn:")
		.append(userDn)
		.append(",userCredentials:")
		.append(getUserCredentials())
		.append(")").toString();
	}
}