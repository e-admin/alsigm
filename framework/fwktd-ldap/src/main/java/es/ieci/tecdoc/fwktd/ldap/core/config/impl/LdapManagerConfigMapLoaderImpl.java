package es.ieci.tecdoc.fwktd.ldap.core.config.impl;

import java.util.Map;

import es.ieci.tecdoc.fwktd.ldap.core.config.LdapManagerConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapConfigurationConstants;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAttributeContainer;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfigVO;

/**
 * Cargador de configuracion del manager LDAP a partir de un map clave-valor
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public class LdapManagerConfigMapLoaderImpl implements LdapManagerConfigLoader {

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
	 * @see es.ieci.tecdoc.fwktd.ldap.core.config.LdapManagerConfigLoader#loadLdapManagerConfig()
	 */
	public LdapManagerConfig loadLdapManagerConfig() throws Exception {

		LdapManagerConfigVO mgrConfig = new LdapManagerConfigVO();
		mgrConfig.setLdapUsersSearchAttribute((String)configMap.get(LdapConfigurationConstants.LDAP_USERS_SEARCH_ATTRIBUTE));
		mgrConfig.setLdapUsersSearchObjectClasses((String)configMap.get(LdapConfigurationConstants.LDAP_USERS_SEARCH_OBJECTCLASSES));
		mgrConfig.setLdapUsersSearchScope((String)configMap.get(LdapConfigurationConstants.LDAP_USERS_SEARCH_SCOPE));
		mgrConfig.setLdapUsersSearchStart((String)configMap.get(LdapConfigurationConstants.LDAP_USERS_SEARCH_START));

		mgrConfig.setLdapGroupsSearchAttribute((String)configMap.get(LdapConfigurationConstants.LDAP_GROUPS_SEARCH_ATTRIBUTE));
		mgrConfig.setLdapGroupsSearchObjectClasses((String)configMap.get(LdapConfigurationConstants.LDAP_GROUPS_SEARCH_OBJECTCLASSES));
		mgrConfig.setLdapGroupsSearchScope((String)configMap.get(LdapConfigurationConstants.LDAP_GROUPS_SEARCH_SCOPE));
		mgrConfig.setLdapGroupsSearchStart((String)configMap.get(LdapConfigurationConstants.LDAP_GROUPS_SEARCH_START));

		mgrConfig.setLdapUsersAttributes((LdapAttributeContainer)configMap.get(LdapConfigurationConstants.LDAP_USERS_ATTRIBUTES));
		mgrConfig.setLdapGroupsAttributes((LdapAttributeContainer)configMap.get(LdapConfigurationConstants.LDAP_GROUPS_ATTRIBUTES));

		return mgrConfig;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		try {
			return loadLdapManagerConfig().toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.config.LdapManagerConfigLoader#initialize(java.util.Map)
	 */
	public void initialize(Map params) throws Exception {
		this.configMap = params;
	}
}
