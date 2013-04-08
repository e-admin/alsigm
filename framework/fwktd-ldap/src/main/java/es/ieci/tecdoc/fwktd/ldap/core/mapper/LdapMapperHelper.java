package es.ieci.tecdoc.fwktd.ldap.core.mapper;

import java.util.ArrayList;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapEntryVO;

/**
 * Helper para obtener entradas propias del conector a partir de objetos estandar LDAP
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public final class LdapMapperHelper {

	/**
	 * Constructor privado
	 */
	private LdapMapperHelper(){

	}

	/**
	 * Permite obtener un objeto con los valores de una entrada estandar LDAP
	 * @param attrs Atributos de la entrada LDAP
	 * @return Objeto {@link LdapEntryVO} con la entrada LDAP
	 * @throws NamingException si se produce alguna excepcion
	 */
	public static LdapEntryVO getEntryFromAttributes(final Attributes attrs) throws NamingException {

		LdapEntryVO ldapEntryVO = new LdapEntryVO();
		if ((attrs!=null)&&(attrs.size()>0)){
			NamingEnumeration allAttributes = attrs.getAll();
			Attribute attr = null;
			String attrId = null;
			NamingEnumeration allAttrValues = null;
			Object value = null;

			while (allAttributes.hasMoreElements()){
				attr = (Attribute) allAttributes.nextElement();
				attrId = attr.getID();
				allAttrValues = attr.getAll();
				ArrayList ltValues = new ArrayList();
				while (allAttrValues.hasMoreElements()){
					value = allAttrValues.nextElement();
					ltValues.add(value);
				}

				allAttrValues.close();

				ldapEntryVO.putAttribute(attrId, ltValues);
			}

			allAttributes.close();
		}
		return ldapEntryVO;
	}

}
