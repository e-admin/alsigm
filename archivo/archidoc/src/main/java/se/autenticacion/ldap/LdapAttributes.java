package se.autenticacion.ldap;

import ieci.tecdoc.core.ldap.LdapConnection;

public class LdapAttributes {

	public static String getNameAttributeName(LdapConnection conn) {
		String name = null;
		int engine = conn.getEngine();
		if (engine == 1)
			name = AD_NAME;
		else if (engine == 2)
			name = IP_NAME;
		else
			name = OL_NAME;
		return name;
	}

	public static String getSurnameAttributeName(LdapConnection conn) {
		String name = null;
		int engine = conn.getEngine();
		if (engine == 1)
			name = AD_SN;
		else if (engine == 2)
			name = IP_SN;
		else
			name = OL_SN;
		return name;
	}

	public static String getMailAttributeName(LdapConnection conn) {
		String name = null;
		int engine = conn.getEngine();
		if (engine == 1)
			name = AD_MAIL;
		else if (engine == 2)
			name = IP_MAIL;
		else
			name = OL_MAIL;
		return name;
	}

	public static String getAddressAttributeName(LdapConnection conn) {
		String name = null;
		int engine = conn.getEngine();
		if (engine == 1)
			name = AD_ADDRESS;
		else if (engine == 2)
			name = IP_ADDRESS;
		else
			name = OL_ADDRESS;
		return name;
	}

	public static String getDescriptionAttributeName(LdapConnection conn) {
		String name = null;
		int engine = conn.getEngine();
		if (engine == 1)
			name = AD_DESCRIPTION;
		else if (engine == 2)
			name = IP_DESCRIPTION;
		else
			name = OL_DESCRIPTION;
		return name;
	}

	private static final String AD_NAME = "givenName";
	private static final String IP_NAME = "givenName";
	private static final String OL_NAME = "givenName";

	private static final String AD_SN = "sn";
	private static final String IP_SN = "sn";
	private static final String OL_SN = "sn";

	private static final String AD_MAIL = "mail";
	private static final String IP_MAIL = "mail";
	private static final String OL_MAIL = "mail";

	private static final String AD_ADDRESS = "address";
	private static final String IP_ADDRESS = "address";
	private static final String OL_ADDRESS = "address";

	private static final String AD_DESCRIPTION = "distinguishedName";
	private static final String IP_DESCRIPTION = "distinguishedName";
	private static final String OL_DESCRIPTION = "distinguishedName";

}
