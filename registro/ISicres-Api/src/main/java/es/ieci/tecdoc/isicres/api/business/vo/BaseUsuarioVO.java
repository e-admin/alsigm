package es.ieci.tecdoc.isicres.api.business.vo;

import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoUsuarioEnum;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

public class BaseUsuarioVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -2235277760333169847L;

	/**
	 * identificador único del vo
	 */
	protected String id;

	protected String loginName;

	protected String fullName;

	protected String password;

	protected String ldapGUID;

	protected boolean isLdapUser;

	protected EstadoUsuarioEnum estado;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLdapGUID() {
		return ldapGUID;
	}

	public void setLdapGUID(String ldapGUID) {
		this.ldapGUID = ldapGUID;
	}

	public Integer getIdInt() {
		if (id != null) {
			return Integer.valueOf(id);
		} else {
			return null;
		}
	}

	public void setId(Integer id) {
		if (id != null) {
			this.id = String.valueOf(id);
		}
	}

	public boolean isLdapUser() {
		return isLdapUser;
	}

	public void setLdapUser(boolean isLdapUser) {
		this.isLdapUser = isLdapUser;
	}

	public EstadoUsuarioEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoUsuarioEnum estado) {
		this.estado = estado;
	}






}
