package es.ieci.tecdoc.fwktd.ldap.core.constants;


/**
 * Clase de utilidad con definicion de claves para atributos de usuarios
 * @author Iecisa
 * @version $Revision: 112 $
 */
public final class LdapUserAttributeConstants {

	/**
	 * Atributo guid - Identificador unico de la entrada.
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: objectGUID</br>
	 * - Sun Directory Manager: nsuniqueid</br>
	 * - Open Ldap: uidnumber</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_GUID = "LDAP_USER_ATTRIBUTE_GUID";

	/**
	 * Atributo name - nombre de la entrada.
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: name</br>
	 * - Sun Directory Manager: givenName</br>
	 * - Open Ldap: givenName</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_NAME = "LDAP_USER_ATTRIBUTE_NAME";

	/**
	 * Atributo surname - Apellido de la entrada.
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: sn</br>
	 * - Sun Directory Manager: sn</br>
	 * - Open Ldap: sn</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_SURNAME = "LDAP_USER_ATTRIBUTE_SURNAME";


	/**
	 * Atributo descripcion - Descripcion de la entrada.
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: description</br>
	 * - Sun Directory Manager: description</br>
	 * - Open Ldap: description</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_DESCRIPTION = "LDAP_USER_ATTRIBUTE_DESCRIPTION";

	/**
	 * Atributo givenName - Nombre de la entrada.
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: givenName</br>
	 * - Sun Directory Manager: givenName</br>
	 * - Open Ldap: givenName</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_GIVEN_NAME = "LDAP_USER_ATTRIBUTE_GIVEN_NAME";

	/**
	 * Atributo displayName - Nombre a mostrar de la entrada.
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: displayName</br>
	 * - Sun Directory Manager: displayName</br>
	 * - Open Ldap: displayName</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_DISPLAY_NAME = "LDAP_USER_ATTRIBUTE_DISPLAY_NAME";

	/**
	 * Atributo commonName - Nombre comun de la entrada.
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: cn</br>
	 * - Sun Directory Manager: cn</br>
	 * - Open Ldap: cn</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_CN = "LDAP_USER_ATTRIBUTE_CN";

	/**
	 * Atributo distinguishedName - Nombre distintivo de la entrada.
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: distinguishedName</br>
	 * - Sun Directory Manager: entryDn</br>
	 * - Open Ldap: entryDn</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_DN = "LDAP_USER_ATTRIBUTE_DN";

	/**
	 * Atributo objectClass - Clases de la entrada.
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: objectClass</br>
	 * - Sun Directory Manager: objectClass</br>
	 * - Open Ldap: objectClass</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_OBJECT_CLASS = "LDAP_USER_ATTRIBUTE_OBJECT_CLASS";

	/**
	 * Atributo accountName - Nombre de la cuenta.
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: sAMAccountName</br>
	 * - Sun Directory Manager: givenName</br>
	 * - Open Ldap: givenName</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_ACCOUNT_NAME = "LDAP_USER_ATTRIBUTE_ACCOUNT_NAME";

	/**
	 * Atributo email - Cuenta de correo electronico.
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: mail</br>
	 * - Sun Directory Manager: mail</br>
	 * - Open Ldap: mail</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_EMAIL = "LDAP_USER_ATTRIBUTE_EMAIL";

	/**
	 * Atributo postalAddress - Direccion postal
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: postalAddress</br>
	 * - Sun Directory Manager: postalAddress</br>
	 * - Open Ldap: postalAddress</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_POSTAL_ADDRESS = "LDAP_USER_ATTRIBUTE_POSTAL_ADDRESS";

	/**
	 * Atributo telephoneNumber - Numero de telefono
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: telephoneNumber</br>
	 * - Sun Directory Manager: telephoneNumber</br>
	 * - Open Ldap: telephoneNumber</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_TELEPHONE_NUMBER = "LDAP_USER_ATTRIBUTE_TELEPHONE_NUMBER";

	/**
	 * Atributo userPrincipalName - Nombre principal de usuario
	 *
	 * <p>
	 * Posibles valores para la clave:</br>
	 * <b>
	 * - Active Directory: userPrincipalName</br>
	 * - Sun Directory Manager: givenName</br>
	 * - Open Ldap: givenName</br>
	 * </b>
	 * </p>
	 */
	public static final String LDAP_USER_ATTRIBUTE_PRINCIPAL_NAME = "LDAP_USER_ATTRIBUTE_PRINCIPAL_NAME";

	/**
	 * Definicion de todos los atributos soportados para usuarios
	 */
	public static final String [] LDAP_USER_ALL_ATTRIBUTES = new String [] {
		LDAP_USER_ATTRIBUTE_ACCOUNT_NAME,
		LDAP_USER_ATTRIBUTE_CN,
		LDAP_USER_ATTRIBUTE_DN,
		LDAP_USER_ATTRIBUTE_DESCRIPTION,
		LDAP_USER_ATTRIBUTE_DISPLAY_NAME,
		LDAP_USER_ATTRIBUTE_EMAIL,
		LDAP_USER_ATTRIBUTE_GIVEN_NAME,
		LDAP_USER_ATTRIBUTE_GUID,
		LDAP_USER_ATTRIBUTE_NAME,
		LDAP_USER_ATTRIBUTE_OBJECT_CLASS,
		LDAP_USER_ATTRIBUTE_POSTAL_ADDRESS,
		LDAP_USER_ATTRIBUTE_SURNAME,
		LDAP_USER_ATTRIBUTE_TELEPHONE_NUMBER,
		LDAP_USER_ATTRIBUTE_PRINCIPAL_NAME
	};

	/**
	 * Constructor privado
	 */
	private LdapUserAttributeConstants(){

	}
}
