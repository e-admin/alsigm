package es.ieci.tecdoc.fwktd.ldap.core.mapper.impl;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import es.ieci.tecdoc.fwktd.ldap.core.mapper.LdapMapper;
import es.ieci.tecdoc.fwktd.ldap.core.mapper.LdapMapperHelper;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapEntryVO;

/**
 * Implementacion de mapeador por defecto
 * @author Iecisa
 * @version $Revision$
 *
 */
public class LdapMapperDefaultImpl implements LdapMapper {

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.mapper.LdapMapper#mapAttributes(javax.naming.directory.Attributes)
	 */
	public LdapEntryVO mapAttributes(final Attributes attrs) throws NamingException {
		return LdapMapperHelper.getEntryFromAttributes(attrs);
	}
}
