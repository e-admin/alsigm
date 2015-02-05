package common.authentication;

/**
 * Clase que almacena los datos específicos de multientidad
 */
public class SessionData {

	/** Id del usuario en el sistema externo */
	String idUsuario;

	/** Tipo del usuario externo */
	String tipoUsuario;

	/** Tipo de autenticacion */
	String tipoAutenticacion;

	/** Guid Ldap */
	String ldapGuid;

	/**
	 * @return el idUsuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario
	 *            el idUsuario a establecer
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return el tipoUsuario
	 */
	public String getTipoUsuario() {
		return tipoUsuario;
	}

	/**
	 * @param tipoUsuario
	 *            el tipoUsuario a establecer
	 */
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	/**
	 * @return the tipoAutenticacion
	 */
	public String getTipoAutenticacion() {
		return tipoAutenticacion;
	}

	/**
	 * @param tipoAutenticacion
	 *            the tipoAutenticacion to set
	 */
	public void setTipoAutenticacion(String tipoAutenticacion) {
		this.tipoAutenticacion = tipoAutenticacion;
	}

	/**
	 * @return the ldapGuid
	 */
	public String getLdapGuid() {
		return ldapGuid;
	}

	/**
	 * @param ldapGuid
	 *            the ldapGuid to set
	 */
	public void setLdapGuid(String ldapGuid) {
		this.ldapGuid = ldapGuid;
	}

}
