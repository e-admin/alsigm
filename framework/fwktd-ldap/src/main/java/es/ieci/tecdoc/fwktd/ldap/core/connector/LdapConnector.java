package es.ieci.tecdoc.fwktd.ldap.core.connector;

import java.util.List;

import es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.mapper.LdapMapper;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserDnVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO;

/**
 * Interface para el conector LDAP
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public interface LdapConnector {

	/**
	 * Permite obtener el mapeador a utilizar
	 * @return mapeador a utilizar
	 */
	LdapMapper getLdapMapper();

	/**
	 * Permite establecer el mapeador a utilizar
	 * @param mapper mapeador a utilizar
	 */
	void setLdapMapper(LdapMapper mapper);

	/**
	 * Permite autenticar un usuario
	 * @param authInfo Informacion de Autenticacion
	 * @param ldapCtxCfgLoader Cargador de Informacion de Contexto
	 * @return guid del usuario autenticado
	 * @throws Exception si se produce alguna excepcion
	 */
	String authenticate (LdapAuthenticationUserDnVO authInfo, LdapContextConfigLoader ldapCtxCfgLoader) throws Exception;

	/**
	 * Permite buscar entradas en el directorio LDAP
	 * @param ldapSearch Definicion de la busqueda
	 * @param ldapCtxCfgLoader Cargador de Informacion de Contexto
	 * @return lista de objetos LdapEntryVO con el resultado de la busqueda
	 * @throws Exception si se produce alguna excepcion
	 */
	List find (LdapSearchVO ldapSearch, LdapContextConfigLoader ldapCtxCfgLoader) throws Exception;

	/**
	 * Permite obtener el guid de una entrada LDAP
	 * @param ldapSearch Definicion de la busqueda
	 * @param ldapCtxCfgLoader Cargador de Informacion de Contexto
	 * @return lista de objetos LdapEntryVO con el resultado de la busqueda
	 * @throws Exception si se produce alguna excepcion
	 */
	String findEntryGuid(LdapSearchVO ldapSearch, LdapContextConfigLoader ldapCtxCfgLoader) throws Exception;

	/**
	 * Permite obtener el dn de una entrada LDAP
	 * @param ldapSearch Definicion de la busqueda
	 * @param ldapCtxCfgLoader Cargador de Informacion de Contexto
	 * @return lista de objetos LdapEntryVO con el resultado de la busqueda
	 * @throws Exception si se produce alguna excepcion
	 */
	String findEntryDn(LdapSearchVO ldapSearch, LdapContextConfigLoader ldapCtxCfgLoader) throws Exception;
}

