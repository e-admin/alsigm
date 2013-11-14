package es.ieci.tecdoc.isicres.admin.core.ldap;

import org.apache.log4j.Logger;

public final class LdapAttribute {
	private static final Logger logger = Logger.getLogger(LdapAttribute.class);

	private static final String AD_OCLS = "objectClass";
	private static final String IP_OCLS = "objectClass";
	private static final String OL_OCLS = "objectClass";


	private LdapAttribute() {
	}

	/**
	 * Obtiene el atributo identificador para los usuarios
	 * @param conn
	 * @return
	 */
	public static String getGuidAttributeName(LdapConnection conn) {
		String name = null;
		int engine;

		engine = conn.getEngine();

		if (engine == LdapEngine.ACTIVE_DIRECTORY) {
			name = LdapAttributeUtils.getLdapAttributeActiveDirectoryGUID();
		} else if (engine == LdapEngine.I_PLANET) {
			name = LdapAttributeUtils.getLdapAttributeIplanetGUID();
		} else {
			name = LdapAttributeUtils.getLdapAttributeOpenLdapGUID();
		}

		return name;
	}

	/**
	 * Obtiene el atributo identificador para los grupos
	 * @param conn
	 * @return
	 */
	public static String getGuidGroupAttributeName(LdapConnection conn) {
		String name = null;
		int engine;

		engine = conn.getEngine();

		if (engine == LdapEngine.ACTIVE_DIRECTORY) {
			name = LdapAttributeUtils.getLdapAttributeGroupActiveDirectoryGUID();
		} else if (engine == LdapEngine.I_PLANET) {
			name = LdapAttributeUtils.getLdapAttributeGroupIplanetGUID();
		} else {
			name = LdapAttributeUtils.getLdapAttributeGroupOpenLdapGUID();
		}

		return name;
	}

	public static String getObjectClassAttributeName(LdapConnection conn) {

		String name = null;
		int engine;

		engine = conn.getEngine();

		if (engine == LdapEngine.ACTIVE_DIRECTORY)
			name = AD_OCLS;
		else if (engine == LdapEngine.I_PLANET)
			name = IP_OCLS;
		else
			name = OL_OCLS;

		return name;

	}



} // class
