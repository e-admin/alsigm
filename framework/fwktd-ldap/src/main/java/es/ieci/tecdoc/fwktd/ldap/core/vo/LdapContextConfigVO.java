package es.ieci.tecdoc.fwktd.ldap.core.vo;


/**
 * Clase con la configuracion base del proxy para conectar a LDAP
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public class LdapContextConfigVO implements LdapContextConfig {

	/**
	 * Motor de Ldap
	 */
	private String ldapEngine;

	/**
	 * Url del servidor
	 */
	private String ldapUrl;

	/**
	 * Base de búsqueda
	 */
	private String ldapBase;

	/**
	 * Usuario
	 */
	private String ldapUser;

	/**
	 * Contraseña del usuario
	 */
	private String ldapPassword;

	/**
	 * Si se utiliza pool
	 */
	private boolean ldapPool;

	/**
	 * Pool timeout
	 */
	private String ldapPoolTimeout;

	/**
	 * Atributo Guid
	 */
	private String ldapGuidAttribute;

	/**
	 * Atributo Dn
	 */
	private String ldapDnAttribute;

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig#getLdapEngine()
	 */
	public String getLdapEngine(){
		return ldapEngine;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion engine
	 * @param engine valor de la propiedad de configuracion engine
	 */
	public void setLdapEngine(final String engine){
		this.ldapEngine = engine;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig#getLdapUrl()
	 */
	public String getLdapUrl(){
		return ldapUrl;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion url
	 * @param url valor de la propiedad de configuracion url
	 */
	public void setLdapUrl(final String url){
		this.ldapUrl = url;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig#getLdapBase()
	 */
	public String getLdapBase(){
		return ldapBase;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion base
	 * @param base valor de la propiedad de configuracion base
	 */
	public void setLdapBase(final String base){
		this.ldapBase = base;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig#getLdapUser()
	 */
	public String getLdapUser(){
		return ldapUser;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion usuario
	 * @param user valor de la propiedad de configuracion usuario
	 */
	public void setLdapUser(final String user){
		this.ldapUser = user;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig#getLdapPassword()
	 */
	public String getLdapPassword(){
		return ldapPassword;
	}

	/**
	 * Permite establever el valor de la propiedad de configuracion contrasenia
	 * @param password valor de la propiedad de configuracion contrasenia
	 */
	public void setLdapPassword(final String password){
		this.ldapPassword = password;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig#isLdapPool()
	 */
	public boolean isLdapPool(){
		return ldapPool;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion pool timeout
	 * @param poolTimeout valor de la propiedad de configuracion pool timeout
	 */
	public void setLdapPoolTimeout(final String poolTimeout){
		this.ldapPoolTimeout = poolTimeout;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig#getLdapPoolTimeout()
	 */
	public String getLdapPoolTimeout(){
		return ldapPoolTimeout;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion pool
	 * @param pool valor de la propiedad de configuracion pool
	 */
	public void setLdapPool(final boolean pool){
		this.ldapPool = pool;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig#getLdapGuidAttribute()
	 */
	public String getLdapGuidAttribute(){
		return ldapGuidAttribute;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion guid attribute
	 * @param guidAttribute valor de la propiedad de configuracion guid attribute
	 */
	public void setLdapGuidAttribute(final String guidAttribute){
		this.ldapGuidAttribute = guidAttribute;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig#getLdapDnAttribute()
	 */
	public String getLdapDnAttribute(){
		return ldapDnAttribute;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion dn attribute
	 * @param dnAttribute valor de la propiedad de configuracion dn attribute
	 */
	public void setLdapDnAttribute(final String dnAttribute){
		this.ldapDnAttribute = dnAttribute;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new StringBuffer()
		.append("(engine:")
		.append(getLdapEngine())
		.append(",url:")
		.append(getLdapUrl())
		.append(",base:")
		.append(getLdapBase())
		.append(",user:")
		.append(getLdapUser())
		.append(",password:")
		.append(getLdapPassword())
		.append(",pool:")
		.append(isLdapPool())
		.append(",poolTimeout:")
		.append(getLdapPoolTimeout())
		.append(",guidAttribute:")
		.append(getLdapGuidAttribute())
		.append(",dnAttribute:")
		.append(getLdapDnAttribute())
		.append(")").toString();
	}
}
