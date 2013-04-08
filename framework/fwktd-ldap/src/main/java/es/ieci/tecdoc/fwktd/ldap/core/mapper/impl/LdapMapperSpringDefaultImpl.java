package es.ieci.tecdoc.fwktd.ldap.core.mapper.impl;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DefaultNameClassPairMapper;

import es.ieci.tecdoc.fwktd.ldap.core.mapper.LdapMapper;
import es.ieci.tecdoc.fwktd.ldap.core.mapper.LdapMapperHelper;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapEntryVO;

/**
 * Implementacion de mapeador utilizando clases de Spring
 * @author Iecisa
 * @version $Revision: 75 $
 *
 */
public class LdapMapperSpringDefaultImpl extends DefaultNameClassPairMapper implements AttributesMapper, LdapMapper {

	/**
	 * {@inheritDoc}
	 * @see org.springframework.ldap.core.AttributesMapper#mapFromAttributes(javax.naming.directory.Attributes)
	 */
	public Object mapFromAttributes(final Attributes attrs) throws NamingException {
		return LdapMapperHelper.getEntryFromAttributes(attrs);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.mapper.LdapMapper#mapAttributes(javax.naming.directory.Attributes)
	 */
	public LdapEntryVO mapAttributes(final Attributes attrs) throws NamingException {
		return LdapMapperHelper.getEntryFromAttributes(attrs);
	}
}
