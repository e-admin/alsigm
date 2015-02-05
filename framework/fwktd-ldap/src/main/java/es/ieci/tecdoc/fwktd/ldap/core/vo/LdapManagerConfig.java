package es.ieci.tecdoc.fwktd.ldap.core.vo;

/**
 * Interfaz para definir los metodos que debería tener un objeto de configuracion del manager
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface LdapManagerConfig {

	/**
	 * Obtiene el valor de la propiedad de configuracion users attribute
	 * @return valor de la propiedad de configuracion users attribute
	 */
	String getLdapUsersSearchAttribute();

	/**
	 * Obtiene el valor de la propiedad de configuracion users object classes
	 * @return valor de la propiedad de configuracion users object classes
	 */
	String getLdapUsersSearchObjectClasses();

	/**
	 * Obtiene el valor de la propiedad de configuracion users scope
	 * @return valor de la propiedad de configuracion users scope
	 */
	String getLdapUsersSearchScope();

	/**
	 * Obtiene el valor de la propiedad de configuracion users start
	 * @return valor de la propiedad de configuracion users start
	 */
	String getLdapUsersSearchStart();

	/**
	 * Obtiene el valor de la propiedad de configuracion groups attribute
	 * @return valor de la propiedad de configuracion groups attribute
	 */
	String getLdapGroupsSearchAttribute();

	/**
	 * Obtiene el valor de la propiedad de configuracion groups object classes
	 * @return valor de la propiedad de configuracion groups object classes
	 */
	String getLdapGroupsSearchObjectClasses();

	/**
	 * Obtiene el valor de la propiedad de configuracion groups scope
	 * @return valor de la propiedad de configuracion groups scope
	 */
	String getLdapGroupsSearchScope();

	/**
	 * Obtiene el valor de la propiedad de configuracion groups start
	 * @return valor de la propiedad de configuracion groups start
	 */
	String getLdapGroupsSearchStart();

	/**
	 * Permite obtener los atributos que se retornan para la busqueda de usuarios
	 * @return objeto con los atributos a retornar en la busqueda de usuarios
	 */
	LdapAttributeContainer getLdapUsersAttributes();

	/**
	 * Permite obtener los atributos que se retornan para la busqueda de grupos
	 * @return objeto con los atributos a retornar en la busqueda de grupos
	 */
	LdapAttributeContainer getLdapGroupsAttributes();

}