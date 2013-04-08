package es.ieci.tecdoc.fwktd.ldap.core.config;

import java.util.Map;

import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfigVO;


/**
 * Interface para el cargador de configuracion del manager LDAP.
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public interface LdapManagerConfigLoader{

	/**
	 * Permite obtener un objeto de configuracion {@link LdapManagerConfigVO}
	 * @return objeto de configuracion {@link LdapManagerConfigVO}
	 * @throws Exception si se produce alguna excepcion
	 */
	LdapManagerConfig loadLdapManagerConfig() throws Exception;

	/**
	 * Permite pasar parametros de inicializacion necesarios para obtener la configuracion
	 * @param params parametros de inicializacion necesarios para obtener la configuracion
	 * @throws Exception si se produce alguna excepcion
	 */
	void initialize(Map params) throws Exception;
}
