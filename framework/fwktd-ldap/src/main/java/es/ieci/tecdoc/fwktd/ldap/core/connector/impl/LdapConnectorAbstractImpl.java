package es.ieci.tecdoc.fwktd.ldap.core.connector.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.connector.LdapConnector;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapConstants;
import es.ieci.tecdoc.fwktd.ldap.core.mapper.LdapMapper;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapEntryAttributeVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapEntryVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO;

/**
 * Implementacion abstracta para el conector LDAP
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public abstract class LdapConnectorAbstractImpl implements LdapConnector {

    /**
     * Mapeador de entrada ldap a objetos
     */
    private LdapMapper ldapMapper;

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.ldap.core.connector.LdapConnector#getLdapMapper()
     */
    public LdapMapper getLdapMapper() {
        return ldapMapper;
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.ldap.core.connector.LdapConnector#setLdapMapper(es.ieci.tecdoc.fwktd.ldap.core.mapper.LdapMapper)
     */
    public void setLdapMapper(final LdapMapper ldapMapper) {
        this.ldapMapper = ldapMapper;
    }

    /**
     * Calcula el dn completo a partir de uno relativo y el dn base
     * @param relativeDn dn relativo
     * @param baseDn dn base
     * @return Dn completo del nodo
     */
    protected String calculateFullDn(final String relativeDn, final String baseDn){
        String fullDn = relativeDn;
        if (StringUtils.isEmpty(relativeDn)) {
            fullDn = baseDn;
        } else if ((StringUtils.isNotEmpty(baseDn)) && (relativeDn.toLowerCase().indexOf(baseDn.toLowerCase())==-1)){
            fullDn = relativeDn+LdapConstants.NODE_SEPARATOR+baseDn;
        }
        return fullDn;
    }

    /**
     * Calcula el dn relativo a partir de uno completo y el dn base
     * @param fullDn dn completo de la entrada
     * @param baseDn dn base
     * @return Dn relativo del nodo
     */
    protected String calculateRelativeDn(final String fullDn, final String baseDn){
        String relativeDn = fullDn;

        if ((StringUtils.isNotEmpty(fullDn))&&(StringUtils.isNotEmpty(baseDn))){
	        int index = fullDn.toLowerCase().indexOf(baseDn.toLowerCase());
	        if (index>0){
	            relativeDn = fullDn.substring(0,index);
	            if (relativeDn.endsWith(LdapConstants.NODE_SEPARATOR)){
	            	relativeDn = relativeDn.substring(0,relativeDn.length()-1);
	            }
	        }
        }

        return relativeDn;
    }

    /**
     * {@inheritDoc}
     * @see es.ieci.tecdoc.fwktd.ldap.core.connector.LdapConnector#findEntryGuid(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO, es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader)
     */
    public String findEntryGuid(final LdapSearchVO ldapSearch,
            final LdapContextConfigLoader ldapCtxCfgLoader) throws Exception {

        LdapContextConfig ldapCtxCfg = ldapCtxCfgLoader.loadLdapContextConfig();

        LdapSearchVO ldapSearchGuid = new LdapSearchVO();
        ldapSearchGuid.setBase(ldapSearch.getBase());
        ldapSearchGuid.setScope(ldapSearch.getScope());
        ldapSearchGuid.setExpected(1);
        ldapSearchGuid.setFilter(ldapSearch.getFilter());
        ldapSearchGuid.setRetAttrs(new String [] {ldapCtxCfg.getLdapGuidAttribute()});

        List result = find(ldapSearchGuid, ldapCtxCfgLoader);
        String guid = null;

        if ((result!=null)&&(!result.isEmpty())){
            LdapEntryVO ldapEntry = (LdapEntryVO) result.get(0);
            LdapEntryAttributeVO ldapEntryAttr = ldapEntry.getAttribute(ldapCtxCfg.getLdapGuidAttribute());
            guid = (String)ldapEntryAttr.getValue();
        }

        return guid;
    }

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.connector.LdapConnector#findEntryDn(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO, es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader)
	 */
	public String findEntryDn(final LdapSearchVO ldapSearch,
            final LdapContextConfigLoader ldapCtxCfgLoader) throws Exception {

		LdapContextConfig ldapCtxCfg = ldapCtxCfgLoader.loadLdapContextConfig();

        LdapSearchVO ldapSearchGuid = new LdapSearchVO();
        ldapSearchGuid.setBase(ldapSearch.getBase());
        ldapSearchGuid.setScope(ldapSearch.getScope());
        ldapSearchGuid.setExpected(1);
        ldapSearchGuid.setFilter(ldapSearch.getFilter());
        ldapSearchGuid.setRetAttrs( new String [] {ldapCtxCfg.getLdapDnAttribute(),ldapCtxCfg.getLdapGuidAttribute()});

        List result = find(ldapSearchGuid, ldapCtxCfgLoader);
        String distinguishedName = null;

        if ((result!=null)&&(!result.isEmpty())){
            LdapEntryVO ldapEntry = (LdapEntryVO) result.get(0);
            distinguishedName = ldapEntry.getFullDn();
        }

        return distinguishedName;
	}
}
