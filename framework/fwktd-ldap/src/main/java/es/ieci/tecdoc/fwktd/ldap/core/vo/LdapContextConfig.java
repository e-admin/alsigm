package es.ieci.tecdoc.fwktd.ldap.core.vo;

/**
 * Interfaz para definir los metodos que debería tener un objeto de configuracion del proxy
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface LdapContextConfig {

	/**
	 * Obtiene el valor de la propiedad de configuracion engine
	 * @return valor de la propiedad de configuracion engine
	 */
	String getLdapEngine();

	/**
	 * Obtiene el valor de la propiedad de configuracion url
	 * @return valor de la propiedad de configuracion url
	 */
	String getLdapUrl();

	/**
	 * Obtiene el valor de la propiedad de configuracion base
	 * @return valor de la propiedad de configuracion base
	 */
	String getLdapBase();

	/**
	 * Obtiene el valor de la propiedad de configuracion usuario
	 * @return valor de la propiedad de configuracion usuario
	 */
	String getLdapUser();

	/**
	 * Obtiene el valor de la propiedad de configuracion contrasenia
	 * @return valor de la propiedad de configuracion contrasenia
	 */
	String getLdapPassword();

	/**
	 * Obtiene el valor de la propiedad de configuracion pool
	 * @return valor de la propiedad de configuracion pool
	 */
	boolean isLdapPool();

	/**
	 * Obtiene el valor de la propiedad de configuracion pool timeout
	 * @return valor de la propiedad de configuracion pool timeout
	 */
	String getLdapPoolTimeout();

	/**
	 * Obtiene el valor de la propiedad de configuracion guid attribute
	 * @return valor de la propiedad de configuracion guid attribute
	 */
	String getLdapGuidAttribute();

	/**
	 * Obtiene el valor de la propiedad de configuracion dn attribute
	 * @return valor de la propiedad de configuracion dn attribute
	 */
	String getLdapDnAttribute();

}