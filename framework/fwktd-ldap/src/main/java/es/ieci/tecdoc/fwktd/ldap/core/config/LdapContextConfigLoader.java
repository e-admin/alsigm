package es.ieci.tecdoc.fwktd.ldap.core.config;

import java.util.Map;

import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfigVO;


/**
 * Interface para el cargador de configuracion LDAP.
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public interface LdapContextConfigLoader{

	/**
	 * Permite obtener un objeto de configuracion {@link LdapContextConfigVO}
	 * @return objeto de configuracion {@link LdapContextConfigVO}
	 * @throws Exception si se produce alguna excepcion
	 */
	LdapContextConfig loadLdapContextConfig() throws Exception;

	/**
	 * Permite pasar parametros de inicializacion necesarios para obtener la configuracion
	 * @param params parametros de inicializacion necesarios para obtener la configuracion
	 * @throws Exception si se produce alguna excepcion
	 */
	void initialize(Map params) throws Exception;

}
