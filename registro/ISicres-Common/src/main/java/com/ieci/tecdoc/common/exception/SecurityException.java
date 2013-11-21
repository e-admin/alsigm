//
// FileName: SecurityException.java
//
package com.ieci.tecdoc.common.exception;

import java.io.Serializable;

/**
 * @author lmvicente
 * @version @since @creationDate 24-mar-2004
 */

public class SecurityException extends TecDocException implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String ERROR_SQL = "securityexception.error_sql";
	public static final String ERROR_USER_NOTFOUND = "securityexception.error_user_notfound";
	public static final String ERROR_NAME_INCORRECT = "securityexception.error_name_incorrect";
	public static final String ERROR_PASSWORD_INCORRECT = "securityexception.error_password_incorrect";
	public static final String ERROR_USER_ISLOCKED = "securityexception.error_user_islocked";
	public static final String ERROR_USER_WASLOCKED = "securityexception.error_user_waslocked";
	public static final String ERROR_USER_APLICATION = "securityexception.error_user_aplication";
	public static final String ERROR_IMPOSSIBLE_LOGOUT = "securityexception.error_impossible_logout";
	public static final String ERROR_IUSEROBJPERM_NOT_FOUND = "securityexception.iuserobjperm_not_found";
	public static final String ERROR_IUSERUSERTYPE_NOT_FOUND = "securityexception.iuserusertype_not_found";
	public static final String ERROR_SCRUSRPERM_NOT_FOUND = "securityexception.scrusrperm_not_found";
	public static final String ERROR_SCROFIC_NOT_FOUND = "securityexception.scrofic_not_found";
	public static final String ERROR_ISICRESAPERMS_NOT_FOUND = "securityexception.isicresaperms_not_found";
	public static final String ERROR_CAN_NOT_CONNECT_LDAP = "securityexception.can_not_connect_ldap";
	public static final String ERROR_CAN_NOT_FIND_USER_ATTRIBUTES_LDAP = "securityexception.can_not_find_user_attributes_ldap";
	public static final String ERROR_CAN_NOT_OPEN_CLOSE_REG= "securityexception.can_not_open_close_reg";
	public static final String ERROR_USER_NOT_PERMS_NECESSARY = "securityexception.user_not_perms_necessary";

	/**
	 * @param key
	 */
	public SecurityException(String key) {
		super(key);
	}

	/**
	 * @param exception
	 */
	public SecurityException(TecDocException exception) {
		super(exception);
	}

	/**
	 * @param key
	 * @param obj
	 */
	public SecurityException(String key, Object obj) {
		super(key, obj);
	}

	/**
	 * @param key
	 * @param objs
	 */
	public SecurityException(String key, Object[] objs) {
		super(key, objs);
	}

	/**
	 * @param key
	 * @param nested
	 */
	public SecurityException(String key, Throwable nested) {
		super(key, nested);
	}

	/**
	 * @param nested
	 */
	public SecurityException(Throwable nested) {
		super(nested);
	}

	/**
	 * @param key
	 * @param obj
	 * @param nested
	 */
	public SecurityException(String key, Object obj, Throwable nested) {
		super(key, obj, nested);
	}

	/**
	 * @param key
	 * @param objs
	 * @param nested
	 */
	public SecurityException(String key, Object[] objs, Throwable nested) {
		super(key, objs, nested);
	}

}
