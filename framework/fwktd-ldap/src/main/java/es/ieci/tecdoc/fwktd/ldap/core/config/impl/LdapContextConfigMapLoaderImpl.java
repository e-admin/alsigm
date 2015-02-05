package es.ieci.tecdoc.fwktd.ldap.core.config.impl;

import java.util.Map;

import es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapConfigurationConstants;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfigVO;

/**
 * Cargador de configuracion LDAP a partir de un map clave-valor
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public class LdapContextConfigMapLoaderImpl implements LdapContextConfigLoader {

	/**
	 * Map con la configuracion LDAP
	 */
	private Map configMap;

	/**
	 * Permite obtener la configuracion LDAP
	 * @return configuracion LDAP
	 */
	public Map getConfigMap() {
		return configMap;
	}

	/**
	 * Permite establecer la configuracion LDAP
	 * @param configMap map con la configuracion LDAP a establecer
	 */
	public void setConfigMap(final Map configMap) {
		this.configMap = configMap;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader#loadLdapContextConfig()
	 */
	public LdapContextConfig loadLdapContextConfig() throws Exception {

		LdapContextConfigVO ctxConfig = new LdapContextConfigVO();
		ctxConfig.setLdapEngine((String)configMap.get(LdapConfigurationConstants.LDAP_ENGINE));
		ctxConfig.setLdapUrl((String)configMap.get(LdapConfigurationConstants.LDAP_URL));
		ctxConfig.setLdapBase((String)configMap.get(LdapConfigurationConstants.LDAP_BASE));
		ctxConfig.setLdapUser((String)configMap.get(LdapConfigurationConstants.LDAP_USER));
		ctxConfig.setLdapPassword((String)configMap.get(LdapConfigurationConstants.LDAP_PASSWORD));
		String ldapPool = (String)configMap.get(LdapConfigurationConstants.LDAP_POOL);
		ctxConfig.setLdapPool("S".equals(ldapPool));
		ctxConfig.setLdapPoolTimeout((String)configMap.get(LdapConfigurationConstants.LDAP_POOL_TIMEOUT));
		ctxConfig.setLdapGuidAttribute((String)configMap.get(LdapConfigurationConstants.LDAP_GUID_ATTRIBUTE));
		ctxConfig.setLdapDnAttribute((String)configMap.get(LdapConfigurationConstants.LDAP_DN_ATTRIBUTE));

		return ctxConfig;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	public String toString()  {
		try {
			return loadLdapContextConfig().toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader#initialize(java.util.Map)
	 */
	public void initialize(Map params) throws Exception {
		this.configMap = params;
	}

}
