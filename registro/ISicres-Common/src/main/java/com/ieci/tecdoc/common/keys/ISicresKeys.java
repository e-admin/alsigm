package com.ieci.tecdoc.common.keys;

public interface ISicresKeys {

	public static final String IS_INVESICRES = "";

	// LDAP
	public static final String LDAP_ATTRIBUTES = "LDAP_ATTRIBUTES_";
	public static final String LDAP_CONFIGURATION = "LDAPINFO";
	public static final String LDAP_SCOPE_BASE = "LDAP_SCOPE_BASE_";
	public static final String LDAP_SCOPE_BASE_SSO = "(&(objectClass=*))";
	public static final String LDAP_SCOPE_SUBTREE1 = "LDAP_SCOPE_SUBTREE1_";
	public static final String LDAP_SCOPE_SUBTREE2 = "LDAP_SCOPE_SUBTREE2_";
	public static final String LDAP_SCOPE_BASESUBTREE_GROUP = "LDAP_SCOPE_BASESUBTREE_GROUP_";
	public static final String LDAP_SCOPEGROUP = "LDAP_SCOPEGROUP_";

	// Registro
	public static final int SCRREGSTATE_IN_BOOK = 1;
	public static final int SCRREGSTATE_OUT_BOOK = 2;
	public static final int BOOK_STATE_OPEN = 0;
	public static final int BOOK_STATE_CLOSED = 1;
	public static final int SCR_NUMERATION_CENTRAL = 0;
	public static final int SCR_NUMERATION_LOCAL = 1;
	public static final int SCR_NUMERATION_OFIC = 2;
	public static final int SCR_USRPERM_ALL = 1;
	public static final int SCR_USRPERM_NOTHING = 0;
	public static final int SCR_ESTADO_REGISTRO_COMPLETO = 0;
	public static final int SCR_ESTADO_REGISTRO_INCOMPLETO = 1;
	public static final int SCR_ESTADO_REGISTRO_RESERVADO = 2;
	public static final int SCR_ESTADO_REGISTRO_ANULADO = 4;
	public static final int SCR_ESTADO_REGISTRO_CERRADO = 5;

	public static final String BOOKTYPE_CONFIGURATION_IN = "BOOKTYPECONFIN";
	public static final String BOOKTYPE_CONFIGURATION_OUT = "BOOKTYPECONFOUT";

	public static final String DISTRIBUCION_AUTOMATICA_MENSAJE = "distribucion.automatica.mensaje.default";


	public static final Integer REGISTRO_ORIGEN_FLD = new Integer(7);
	public static final Integer REGISTRO_DESTINO_FLD = new Integer(8);

	public static final String GUION_BAJO = "_";
}
