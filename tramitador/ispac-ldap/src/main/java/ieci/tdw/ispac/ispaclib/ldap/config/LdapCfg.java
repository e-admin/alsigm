package ieci.tdw.ispac.ispaclib.ldap.config;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.PropertiesConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.util.Hashtable;

import javax.naming.Context;

/**
 * Clase que lee la configuración de LDAP.
 *
 */
public class LdapCfg extends PropertiesConfiguration {

	private static LdapCfg mInstance = null;

	private static final String DEFAULT_CONFIG_FILENAME = "ispacldap.properties";

	private static final String CTX_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	
	public final static String LDAP_SERVER = "LDAP_SERVER";
	public final static String LDAP_ADMUSER = "LDAP_ADMUSER";
	public final static String LDAP_ADMPASS = "LDAP_ADMPASS";
	public final static String LDAP_ROOTDN = "LDAP_ROOTDN";
	public final static String LDAP_GROUPS_ROOTDN = "LDAP_GROUPS_ROOTDN";
	public final static String LDAP_ORG_ROOTDN = "LDAP_ORG_ROOTDN";
	public final static String CN_ATTNAME = "CN_ATTNAME";
	public final static String GUID_ATTNAME = "GUID_ATTNAME";
	public final static String MEMBER_ATTNAME = "MEMBER_ATTNAME";
	public final static String PERSON_OBJECTCLASS = "PERSON_OBJECTCLASS";
	public final static String PERSON_LABEL_ATTNAME = "PERSON_LABEL_ATTNAME";
	public final static String PERSON_LOGIN_ATTNAME = "PERSON_LOGIN_ATTNAME";
	public final static String GROUP_OBJECTCLASS = "GROUP_OBJECTCLASS";
	public final static String GROUP_LABEL_ATTNAME = "GROUP_LABEL_ATTNAME";
	public final static String UNIT_OBJECTCLASS = "UNIT_OBJECTCLASS";
	public final static String UNIT_LABEL_ATTNAME = "UNIT_LABEL_ATTNAME";
	public final static String ORGANIZATION_OBJECTCLASS = "ORGANIZATION_OBJECTCLASS";
	public final static String ORGANIZATION_LABEL_ATTNAME = "ORGANIZATION_LABEL_ATTNAME";
	public final static String DOMAIN_OBJECTCLASS = "DOMAIN_OBJECTCLASS";
	public final static String DOMAIN_LABEL_ATTNAME = "DOMAIN_LABEL_ATTNAME";
	public final static String CONNECT_TIMEOUT = "CONNECT_TIMEOUT";
	
	public final static String LDAP_TYPE = "LDAP_TYPE";
	
	public final static int LDAPTYPE_DEFAULT 		= 1;
    public final static int LDAPTYPE_IPLANET 		= 2;
    public final static int LDAPTYPE_ACTIVEDIR 		= 3;
    public final static int LDAPTYPE_OID 			= 4;
    public final static int LDAPTYPE_OPENLDAP 		= 5;
    public final static int LDAPTYPE_SUNDIRSERVER	= 6;
	
	private int ldaptype;
	

	/**
	 * Constructor.
	 */
	protected LdapCfg() {
		super();
	}

	/**
	 * Obtiene una instancia de la clase.
	 * @return Instancia de la clase.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized LdapCfg getInstance() throws ISPACException {
		
		if (mInstance == null) {
			mInstance = new LdapCfg();
			mInstance.initiate(DEFAULT_CONFIG_FILENAME);
		}
		return mInstance;
	}

	protected void initiate(String defaultConfigFileName) throws ISPACException {
		
		super.initiate(defaultConfigFileName);

		// Establecer el tipo de LDAP
		this.ldaptype = TypeConverter.parseInt(get(LDAP_TYPE), LDAPTYPE_DEFAULT);
	}

	/**
	 * Dice si se trata de una configuracion AD o no
	 * 
	 * @return true si es una configuracion AD
	 */
	public boolean isBinaryUUID() {
		return (ldaptype == LDAPTYPE_ACTIVEDIR);
	}

	/**
	 * Devuelve un tabla hash con la configuracion del directorio
	 * 
	 * @return tabla hash con la configuracion del directorio
	 */
	public Hashtable getCnxEnv() {
		Hashtable env = new Hashtable(); // Parámetros de la conexión
		env.put(Context.INITIAL_CONTEXT_FACTORY, LdapCfg.CTX_FACTORY);
		env.put(Context.PROVIDER_URL, get(LDAP_SERVER));
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		if (ldaptype == LDAPTYPE_ACTIVEDIR) {
			env.put("java.naming.ldap.attributes.binary", get(GUID_ATTNAME));
			env.put(Context.REFERRAL, "follow");
		}

		env.put("com.sun.jndi.ldap.connect.timeout", get(CONNECT_TIMEOUT));
		
		//		env.put("java.naming.ldap.version", "2");
		// Trazas
		//		env.put("com.sun.jndi.ldap.trace.ber", System.out);
		
		return env;
	}

	/**
	 * Convierte un dn en absoluto
	 * 
	 * @param dn relativo
	 * @return dn absoluto
	 */
	public String toAbsoluteDN(String dn) {
		String rootdn = get(LDAP_ROOTDN);
		String absDN = null;
		if (rootdn.equals(""))
			absDN = dn;
		else {
			if (!dn.equals(""))
				absDN = dn + "," + rootdn;
			else
				absDN = rootdn;
		}

		return absDN;
	}

	/**
	 * Devuelve el dn relativo
	 * 
	 * @param dn dn absoluto
	 * @return dn relativo
	 * @throws si el dn absoluto no pertenece al dn root
	 */
	public String toRelativeDN(String dn) throws ISPACException {
		String rootdn = get(LDAP_ROOTDN);
		int idx = dn.indexOf(rootdn);
		if (idx == -1)
			throw new ISPACException("LdapCfg::toRelativeDN(" + dn + ")");
		if (dn.equals(rootdn))
			return "";
		return dn.substring(0, idx - 1);
	}

	/**
	 * Informa de si el dn esta o no dentro del root dn
	 */
	public boolean belongToRootDN(String dn) {
		String orgrootdn = getOrgRootDN();
		String grprootdn = getGroupsRootDN();
		return ((dn.indexOf(orgrootdn) + orgrootdn.length() == dn.length())
				|| (dn.indexOf(grprootdn) + grprootdn.length() == dn.length()));
	}

	public String get(String key) {
		return (String) super.get(key);
	}

	public void put(String key, String value) {
		super.put(key, value);
	}

	public String getServer() {
		return get(LDAP_SERVER);
	}

	public String getAdminUser() {
		return get(LDAP_ADMUSER);
	}

	public String getAdminPwd() {
		return get(LDAP_ADMPASS);
	}

	public String getRootDN() {
		return get(LDAP_ROOTDN);
	}

	public String getGroupsRootDN() {
		String groupsRootDN = get(LDAP_GROUPS_ROOTDN);
		if (StringUtils.isBlank(groupsRootDN)) {
			groupsRootDN = get(LDAP_ROOTDN);
		}
		return groupsRootDN;
	}

	public String getOrgRootDN() {
		String orgRootDN = get(LDAP_ORG_ROOTDN);
		if (StringUtils.isBlank(orgRootDN)) {
			orgRootDN = get(LDAP_ROOTDN);
		}
		return orgRootDN;
	}

	public String getCNAttName() {
		return get(CN_ATTNAME);
	}

	public String getGUIDAttName() {
		return get(GUID_ATTNAME);
	}

	public String getMemberAttName() {
		return get(MEMBER_ATTNAME);
	}

	public String getPersonObjectClass() {
		return get(PERSON_OBJECTCLASS);
	}

	public String getPersonLabelAttName() {
		return StringUtils.defaultIfBlank(get(PERSON_LABEL_ATTNAME), getCNAttName());
	}

	public String getPersonLoginAttName() {
		return StringUtils.defaultIfBlank(get(PERSON_LOGIN_ATTNAME), getCNAttName());
	}

	public String getGroupObjectClass() {
		return get(GROUP_OBJECTCLASS);
	}

	public String getGroupLabelAttName() {
		return StringUtils.defaultIfBlank(get(GROUP_LABEL_ATTNAME), getCNAttName());
	}

	public String getUnitObjectClass() {
		return get(UNIT_OBJECTCLASS);
	}

	public String getUnitLabelAttName() {
		return StringUtils.defaultIfBlank(get(UNIT_LABEL_ATTNAME), getCNAttName());
	}

	public String getOrganizationObjectClass() {
		return get(ORGANIZATION_OBJECTCLASS);
	}

	public String getOrganizationLabelAttName() {
		return StringUtils.defaultIfBlank(get(ORGANIZATION_LABEL_ATTNAME), getCNAttName());
	}

	public String getDomainObjectClass() {
		return get(DOMAIN_OBJECTCLASS);
	}

	public String getDomainLabelAttName() {
		return StringUtils.defaultIfBlank(get(DOMAIN_LABEL_ATTNAME), getCNAttName());
	}

}