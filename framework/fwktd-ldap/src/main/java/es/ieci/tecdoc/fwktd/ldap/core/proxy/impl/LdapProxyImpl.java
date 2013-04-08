package es.ieci.tecdoc.fwktd.ldap.core.proxy.impl;

import java.util.List;
import java.util.Map;

import es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.connector.LdapConnector;
import es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserDnVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO;

/**
 * Proxy con los metodos del conector LDAP
 * @author Iecisa
 * @version $Revision: 114 $
 *
 */
public class LdapProxyImpl implements LdapProxy {

	/**
	 * Cargador de configuracion
	 */
	private LdapContextConfigLoader ldapCtxCfgLoader;

	/**
	 * Conector LDAP
	 */
	private LdapConnector ldapConnector;

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy#getLdapContextConfigLoader()
	 */
	public LdapContextConfigLoader getLdapContextConfigLoader() {
		return ldapCtxCfgLoader;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy#setLdapContextConfigLoader(es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader)
	 */
	public void setLdapContextConfigLoader(final LdapContextConfigLoader ldapCtxCfgLdr) {
		this.ldapCtxCfgLoader = ldapCtxCfgLdr;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy#getLdapConnector()
	 */
	public LdapConnector getLdapConnector() {
		return ldapConnector;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy#setLdapConnector(es.ieci.tecdoc.fwktd.ldap.core.connector.LdapConnector)
	 */
	public void setLdapConnector(final LdapConnector ldapConnector) {
		this.ldapConnector = ldapConnector;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy#initialize(java.util.Map)
	 */
	public void initialize(Map params) throws Exception {
		this.ldapCtxCfgLoader.initialize(params);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy#authenticate(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserDnVO)
	 */
	public String authenticate (final LdapAuthenticationUserDnVO authInfo) throws Exception {
		return ldapConnector.authenticate(authInfo, ldapCtxCfgLoader);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy#find(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO)
	 */
	public List find (final LdapSearchVO ldapSearch) throws Exception {
		return ldapConnector.find(ldapSearch, ldapCtxCfgLoader);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy#findEntryGuid(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO)
	 */
	public String findEntryGuid (final LdapSearchVO ldapSearch) throws Exception {
		return ldapConnector.findEntryGuid(ldapSearch, ldapCtxCfgLoader);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.proxy.LdapProxy#findEntryDn(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO)
	 */
	public String findEntryDn (final LdapSearchVO ldapSearch) throws Exception {
		return ldapConnector.findEntryDn(ldapSearch, ldapCtxCfgLoader);
	}
}
