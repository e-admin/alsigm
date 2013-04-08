package es.ieci.tecdoc.fwktd.ldap.core.proxy;

import java.util.List;
import java.util.Map;

import es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.connector.LdapConnector;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserDnVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO;

/**
 * Interface para el proxy LDAP
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public interface LdapProxy {

	/**
	 * Permite obtener el cargador de configuracion
	 * @return Objeto {@link LdapContextConfigLoader} cargador de configuracion
	 */
	LdapContextConfigLoader getLdapContextConfigLoader();

	/**
	 * Permite establecer el cargador de configuracion
	 * @param ldapCtxCfgLdr Objeto {@link LdapContextConfigLoader} cargador de configuracion
	 */
	void setLdapContextConfigLoader(final LdapContextConfigLoader ldapCtxCfgLdr);

	/**
	 * Permite obtener el conector LDAP
	 * @return Objeto {@link LdapConnector} conector de LDAP
	 */
	LdapConnector getLdapConnector();

	/**
	 * Permite establecer el conector LDAP
	 * @param ldapConnector Objeto {@link LdapConnector} conector de LDAP
	 */
	void setLdapConnector(final LdapConnector ldapConnector);

	/**
	 * Permite pasar parametros de inicializacion necesarios para obtener la configuracion
	 * @param params parametros de inicializacion necesarios para obtener la configuracion
	 * @throws Exception si se produce alguna excepcion
	 */
	void initialize(Map params) throws Exception;

	/**
	 * Metodo para autenticar usuarios
	 * @param authInfo Objeto {@link LdapAuthenticationUserDnVO} con la informacion de autenticacion
	 * @return guid del usuario autenticado
	 * @throws Exception si se produce alguna excepcion
	 */
	String authenticate (final LdapAuthenticationUserDnVO authInfo) throws Exception;

	/**
	 * Metodo para buscar entradas
	 * @param ldapSearch Objeto {@link LdapSearchVO} con la informacion de busqueda
	 * @return Lista de entradas LDAP
	 * @throws Exception si se produce alguna excepcion
	 */
	List find (final LdapSearchVO ldapSearch) throws Exception;

	/**
	 * Metodo para buscar el guid de una entrada
	 * @param ldapSearch Objeto {@link LdapSearchVO} con la informacion de busqueda
	 * @return Guid de la entrada deseada
	 * @throws Exception si se produce alguna excepcion
	 */
	String findEntryGuid (final LdapSearchVO ldapSearch) throws Exception;

	/**
	 * Metodo para buscar el dn de una entrada
	 * @param ldapSearch Objeto {@link LdapSearchVO} con la informacion de busqueda
	 * @return Dn de la entrada deseada
	 * @throws Exception si se produce alguna excepcion
	 */
	String findEntryDn (final LdapSearchVO ldapSearch) throws Exception;
}
