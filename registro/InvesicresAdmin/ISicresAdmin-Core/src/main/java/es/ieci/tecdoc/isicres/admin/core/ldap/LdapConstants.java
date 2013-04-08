package es.ieci.tecdoc.isicres.admin.core.ldap;

public class LdapConstants {

	public static final byte PERSON = 1;
    public static final byte GROUP = 2;    
    public static final byte ORGANIZATIONAL_UNIT = 4;
    public static final byte DEPARTMENT = 8;
    public static final byte OTHER = 16;
    
    // Tipos de árboles ldap
    public static final int LDAP_TYPE_TREE_USER = 1;
    public static final int LDAP_TYPE_TREE_GROUP = 2;
    public static final int LDAP_TYPE_TREE_USER_AND_GROUP = 4;
}
