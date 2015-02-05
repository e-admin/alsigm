package es.ieci.tecdoc.fwktd.ldap.core.constants;

import javax.naming.directory.SearchControls;


/**
 * Clase de utilidad para ambitos de ldap
 * @author Iecisa
 * @version $Revision: 79 $
 */
public final class LdapScopeConstants {

	/**
	 * Ambito de busqueda objeto
	 */
	public static final String SCOPE_OBJECT = String.valueOf(SearchControls.OBJECT_SCOPE);

	/**
	 * Ambito de busqueda un nivel
	 */
	public static final String SCOPE_ONELEVEL = String.valueOf(SearchControls.ONELEVEL_SCOPE);

	/**
	 * Ambito de busqueda subarbol
	 */
	public static final String SCOPE_SUBTREE = String.valueOf(SearchControls.SUBTREE_SCOPE);

	/**
	 * Constructor privado
	 */
	private LdapScopeConstants(){

	}
}
