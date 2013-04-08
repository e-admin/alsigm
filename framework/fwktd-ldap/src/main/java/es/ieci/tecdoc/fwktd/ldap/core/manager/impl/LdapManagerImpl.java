package es.ieci.tecdoc.fwktd.ldap.core.manager.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.ldap.core.config.LdapManagerConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.exception.LdapException;
import es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager;
import es.ieci.tecdoc.fwktd.ldap.core.messages.LdapErrorCodes;
import es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy;
import es.ieci.tecdoc.fwktd.ldap.core.utils.LdapExceptionUtils;
import es.ieci.tecdoc.fwktd.ldap.core.utils.LdapFilterUtils;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAttributeContainer;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserDnVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserNameVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchManagerVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO;

/**
 * Manager Ldap
 * 
 * @author Iecisa
 * @version $Revision: 115 $
 * 
 */
public class LdapManagerImpl implements LdapManager {

	/**
	 * Logger para la clase
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LdapManagerImpl.class);

	/**
	 * Proxy para acceso a LDOOAP
	 */
	private LdapProxy ldapProxy;

	/**
	 * Cargador de configuracion para el manager
	 */
	private LdapManagerConfigLoader ldapMgrCfgLoader;

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#getLdapProxy()
	 */
	public LdapProxy getLdapProxy() {
		return ldapProxy;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#setLdapProxy(es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy)
	 */
	public void setLdapProxy(final LdapProxy ldapProxy) {
		this.ldapProxy = ldapProxy;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#getLdapManagerConfigLoader()
	 */
	public LdapManagerConfigLoader getLdapManagerConfigLoader() {
		return ldapMgrCfgLoader;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#setLdapManagerConfigLoader(es.ieci.tecdoc.fwktd.ldap.core.config.LdapManagerConfigLoader)
	 */
	public void setLdapManagerConfigLoader(
			final LdapManagerConfigLoader ldapMgrCfgLdr) {
		this.ldapMgrCfgLoader = ldapMgrCfgLdr;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#initialize(java.util.Map)
	 */
	public void initialize(Map params) throws Exception {
		this.ldapProxy.initialize(params);
		this.ldapMgrCfgLoader.initialize(params);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#authenticate(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserNameVO)
	 */
	public String authenticate(final LdapAuthenticationUserNameVO authInfo)
			throws Exception {

		LdapManagerConfig ldapMgrCfg = ldapMgrCfgLoader.loadLdapManagerConfig();

		LdapSearchVO ldapSearch = new LdapSearchVO();
		ldapSearch.setBase(ldapMgrCfg.getLdapUsersSearchStart());
		ldapSearch
				.setFilter(LdapFilterUtils
						.createFilterForObjectClassAntAttrValue(ldapMgrCfg
								.getLdapUsersSearchObjectClasses(), ldapMgrCfg
								.getLdapUsersSearchAttribute(), authInfo
								.getUserName()));
		ldapSearch.setExpected(1);
		ldapSearch.setScope(ldapMgrCfg.getLdapUsersSearchScope());

		String distinguishedName = null;
		try {
			distinguishedName = ldapProxy.findEntryDn(ldapSearch);
		} catch (LdapException e) {
			LdapExceptionUtils.generateErrorException(
					LdapErrorCodes.ERR_10011_NODE_NOT_FOUND_ERROR, LOGGER);
		}

		if (distinguishedName == null) {
			LdapExceptionUtils.generateErrorException(
					LdapErrorCodes.ERR_10011_NODE_NOT_FOUND_ERROR, LOGGER);
		}

		LdapAuthenticationUserDnVO authConnector = new LdapAuthenticationUserDnVO();
		authConnector.setUserDn(distinguishedName);
		authConnector.setUserCredentials(authInfo.getUserCredentials());

		return ldapProxy.authenticate(authConnector);
	}

	/**
	 * Permite obtener todos los atributos definidos para un map
	 * 
	 * @param retAttrs
	 *            Atributos originales
	 * @return Array de atributos a obtener
	 */
	private String[] convertReturningAttributes(
			final LdapAttributeContainer retAttrs) {
		String[] returningAttrs = null;
		if ((retAttrs != null) && (!retAttrs.isEmpty())) {
			returningAttrs = new String[retAttrs.size()];
			Iterator itAttrs = retAttrs.keySet().iterator();
			int count = 0;
			while (itAttrs.hasNext()) {
				Object key = (String) itAttrs.next();
				String value = (String) retAttrs.get(key);
				if (value != null) {
					returningAttrs[count] = value;
				}
				count++;
			}
		}
		return returningAttrs;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#getUsersReturningAttribute(java.lang.String)
	 */
	public String getUsersReturningAttribute(final String key) throws Exception {
		String retValue = null;

		LdapManagerConfig ldapMgrCfg = ldapMgrCfgLoader.loadLdapManagerConfig();
		if ((ldapMgrCfg != null)
				&& (ldapMgrCfg.getLdapUsersAttributes() != null)) {
			retValue = (String) ldapMgrCfg.getLdapUsersAttributes().get(key);
		}
		return retValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#getGroupsReturningAttribute(java.lang.String)
	 */
	public String getGroupsReturningAttribute(final String key)
			throws Exception {
		String retValue = null;

		LdapManagerConfig ldapMgrCfg = ldapMgrCfgLoader.loadLdapManagerConfig();
		if ((ldapMgrCfg != null)
				&& (ldapMgrCfg.getLdapGroupsAttributes() != null)) {
			retValue = (String) ldapMgrCfg.getLdapGroupsAttributes().get(key);
		}
		return retValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#getUsersReturningAttributes()
	 */
	public String[] getUsersReturningAttributes() throws Exception {
		LdapManagerConfig ldapMgrCfg = ldapMgrCfgLoader.loadLdapManagerConfig();
		return convertReturningAttributes(ldapMgrCfg.getLdapUsersAttributes());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#getGroupsReturningAttributes()
	 */
	public String[] getGroupsReturningAttributes() throws Exception {
		LdapManagerConfig ldapMgrCfg = ldapMgrCfgLoader.loadLdapManagerConfig();
		return convertReturningAttributes(ldapMgrCfg.getLdapGroupsAttributes());
	}

	/**
	 * Rellena el VO de busqueda de usuarios para el proxy
	 * 
	 * @param searchMgrVO
	 *            VO de busqueda del manager
	 * @return VO de busqueda de usuarios para el proxy
	 */
	private LdapSearchVO getLdapSearchUsers(
			final LdapSearchManagerVO searchMgrVO) throws Exception {
		LdapManagerConfig ldapMgrCfg = ldapMgrCfgLoader.loadLdapManagerConfig();
		LdapSearchVO searchVO = new LdapSearchVO();

		if (searchMgrVO.isAddUserClasses()) {
			searchVO.setFilter(LdapFilterUtils
					.createFilterForObjectClassAndFilter(ldapMgrCfg
							.getLdapUsersSearchObjectClasses(), searchMgrVO
							.getFilter()));
		} else {
			searchVO.setFilter(searchMgrVO.getFilter());
		}
		searchVO.setRetAttrs(convertReturningAttributes(ldapMgrCfg
				.getLdapUsersAttributes()));

		if (StringUtils.isNotEmpty(searchMgrVO.getScope())) {
			searchVO.setScope(searchMgrVO.getScope());
		} else {
			searchVO.setScope(ldapMgrCfg.getLdapUsersSearchScope());
		}

		if (StringUtils.isNotEmpty(searchMgrVO.getBase())) {
			searchVO.setBase(searchMgrVO.getBase());
		} else {
			searchVO.setBase(ldapMgrCfg.getLdapUsersSearchStart());
		}

		return searchVO;
	}

	/**
	 * Rellena el VO de busqueda de grupos para el proxy
	 * 
	 * @param searchMgrVO
	 *            VO de busqueda del manager
	 * @return VO de busqueda de grupos para el proxy
	 */
	private LdapSearchVO getLdapSearchGroups(
			final LdapSearchManagerVO searchMgrVO) throws Exception {
		LdapManagerConfig ldapMgrCfg = ldapMgrCfgLoader.loadLdapManagerConfig();
		LdapSearchVO searchVO = new LdapSearchVO();

		if (searchMgrVO.isAddGroupClasses()) {
			searchVO.setFilter(LdapFilterUtils
					.createFilterForObjectClassAndFilter(ldapMgrCfg
							.getLdapGroupsSearchObjectClasses(), searchMgrVO
							.getFilter()));
		} else {
			searchVO.setFilter(searchMgrVO.getFilter());
		}
		searchVO.setRetAttrs(convertReturningAttributes(ldapMgrCfg
				.getLdapGroupsAttributes()));

		if (searchMgrVO.getScope() != null) {
			searchVO.setScope(searchMgrVO.getScope());
		} else {
			searchVO.setScope(ldapMgrCfg.getLdapGroupsSearchScope());
		}

		if (searchMgrVO.getBase() != null) {
			searchVO.setBase(searchMgrVO.getBase());
		} else {
			searchVO.setBase(ldapMgrCfg.getLdapGroupsSearchStart());
		}

		return searchVO;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#findUsers(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchManagerVO)
	 */
	public List findUsers(final LdapSearchManagerVO searchMgrVO)
			throws Exception {
		LdapSearchVO searchVO = getLdapSearchUsers(searchMgrVO);
		return ldapProxy.find(searchVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#findUser(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchManagerVO)
	 */
	public List findUser(final LdapSearchManagerVO searchMgrVO)
			throws Exception {
		LdapSearchVO searchVO = getLdapSearchUsers(searchMgrVO);
		searchVO.setExpected(1);
		return ldapProxy.find(searchVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#findUserGroups(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchManagerVO)
	 */
	public List findUserGroups(final LdapSearchManagerVO searchMgrVO)
			throws Exception {
		LdapSearchVO searchVO = getLdapSearchGroups(searchMgrVO);
		return ldapProxy.find(searchVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.ldap.core.manager.LdapManager#find(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO)
	 */
	public List find(final LdapSearchVO searchVO) throws Exception {
		return ldapProxy.find(searchVO);
	}

}
