package ieci.tecdoc.isicres.rpadmin.struts.acciones.ldap;

/**
 * Clase con definición de constantes con los errores LDAP
 */
public class LdapException {

	private static final long serialVersionUID = 1L;
	public static final long EXC_GENERIC_EXCEPCION	= 38670000; 
	public static final long LDAP_NODE_NOT_FOUND = EXC_GENERIC_EXCEPCION+1;
	public static final long LDAP_TREE_CONFIG_NOT_FOUND = EXC_GENERIC_EXCEPCION+2;

}
