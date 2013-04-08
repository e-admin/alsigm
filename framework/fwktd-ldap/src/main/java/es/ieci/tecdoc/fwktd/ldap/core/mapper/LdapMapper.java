package es.ieci.tecdoc.fwktd.ldap.core.mapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapEntryVO;

/**
 * Mapeador a objetos de las entradas LDAP
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public interface LdapMapper {

	/**
	 * Convierte una entrada estandar de LDAP en un objeto propio del conector
	 * @param attrs Atributos de la entrada
	 * @return Objeto {@link LdapEntryVO} con la informacion de la entrada
	 * @throws NamingException si se produce alguna excepcion
	 */
	LdapEntryVO mapAttributes(Attributes attrs) throws NamingException;
}
