package es.ieci.tecdoc.fwktd.ldap.core.manager;

import java.util.List;
import java.util.Map;

import es.ieci.tecdoc.fwktd.ldap.core.config.LdapManagerConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapGroupAttributeConstants;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapUserAttributeConstants;
import es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserNameVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapEntryVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchManagerVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO;

/**
 * Interface para el manager LDAP
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public interface LdapManager {


	/**
	 * Devuelve el proxy para acceso a LDAP
	 * @return Proxy ({@link LdapProxy}) para acceso LDAP
	 */
	LdapProxy getLdapProxy();

	/**
	 * Permite establecer el proxy ldap
	 * @param ldapProxy proxy ldap
	 */
	void setLdapProxy(final LdapProxy ldapProxy);

	/**
	 * Permite obtener el cargador de configuracion
	 * @return Objeto {@link LdapManagerConfigLoader} cargador de configuracion
	 */
	LdapManagerConfigLoader getLdapManagerConfigLoader();

	/**
	 * Permite establecer el cargador de configuracion
	 * @param ldapMgrCfgLdr Objeto {@link LdapManagerConfigLoader} cargador de configuracion
	 */
	void setLdapManagerConfigLoader(final LdapManagerConfigLoader ldapMgrCfgLdr);

	/**
	 * Permite pasar parametros de inicializacion necesarios para obtener la configuracion
	 * @param params parametros de inicializacion necesarios para obtener la configuracion
	 * @throws Exception si se produce alguna excepcion
	 */
	void initialize(Map params) throws Exception;

	/**
	 * Permite obtener cualquiera de los atributos posibles para los usuarios. Los posibles
	 * valores estan definidos en ({@link LdapUserAttributeConstants})
	 * @param key Clave del atributo a obtener
	 * @return nombre del atributo a obtener
	 * @throws Exception si se produce alguna excepcion
	 */
	String getUsersReturningAttribute (final String key) throws Exception;

	/**
	 * Permite obtener los atributos de los usuarios. Los posibles
	 * valores estan definidos en ({@link LdapUserAttributeConstants})
	 * @return Nombres de todos los atributos
	 * @throws Exception si se produce alguna excepcion
	 */
	String [] getUsersReturningAttributes () throws Exception;

	/**
	 * Permite obtener cualquiera de los atributos posibles para los grupos. Los posibles
	 * valores estan definidos en ({@link LdapGroupAttributeConstants})
	 * @param key Clave del atributo a obtener
	 * @return nombre del atributo a obtener
	 * @throws Exception si se produce alguna excepcion
	 */
	String getGroupsReturningAttribute (final String key) throws Exception;

	/**
	 * Permite obtener los atributos de los usuarios. Los posibles
	 * valores estan definidos en ({@link LdapGroupAttributeConstants})
	 * @return Nombres de todos los atributos
	 * @throws Exception si se produce alguna excepcion
	 */
	String [] getGroupsReturningAttributes () throws Exception;

	/**
	 * Permite autenticar un usuario
	 * @param authInfo Informacion de Autenticacion
	 * @return guid del usuario autenticado
	 * @throws Exception si se produce alguna excepcion
	 */
	String authenticate (final LdapAuthenticationUserNameVO authInfo) throws Exception;

	/**
	 * Permite buscar usuarios en el sistema
	 * @param searchMgrVO objeto con la informacion de la busqueda
	 * @return Lista de resultados({@link LdapEntryVO}})
	 * @throws Exception si se produce alguna excepcion
	 */
	List findUsers(final LdapSearchManagerVO searchMgrVO) throws Exception;

	/**
	 * Permite buscar un usuario en el sistema
	 * @param searchMgrVO objeto con la informacion de la busqueda
	 * @return Lista de resultados({@link LdapEntryVO}})
	 * @throws Exception si se produce alguna excepcion
	 */
	List findUser(final LdapSearchManagerVO searchMgrVO) throws Exception;

	/**
	 * Permite buscar grupos de usuarios en el sistema
	 * @param searchMgrVO objeto con la informacion de la busqueda
	 * @return Lista de resultados({@link LdapEntryVO}})
	 * @throws Exception si se produce alguna excepcion
	 */
	List findUserGroups(final LdapSearchManagerVO searchMgrVO) throws Exception;

	/**
	 * Permite buscar cualquier entrada del arbol ldap
	 * @param searchVO objeto de busqueda
	 * @return Lista de resultados({@linkplain LdapEntryVO}})
	 * @throws Exception si se produce alguna excepcion
	 */
	List find (final LdapSearchVO searchVO) throws Exception;
}
