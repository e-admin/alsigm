//
// FileName: ValidationException.java
//
package com.ieci.tecdoc.common.exception;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @author lmvicente
 * @version @since @creationDate 24-mar-2004
 */

public class ValidationException extends TecDocException implements Serializable {

	private static Logger log = Logger.getLogger(ValidationException.class);

	public static final String ATTRIBUTE_USER = "user";
	public static final String ATTRIBUTE_PASSWORD = "password";
	public static final String ATTRIBUTE_SESSION = "session";
	public static final String ATTRIBUTE_BOOK = "book";
	public static final String ATTRIBUTE_PROV = "prov";
	public static final String ATTRIBUTE_PERSON = "person";
	public static final String ATTRIBUTE_AXSFQUERY = "axsfQuery";
	public static final String ATTRIBUTE_NAVIGATIONTYPE = "navigationType";
	public static final String ATTRIBUTE_VALIDATION_FIELD_VALUE = "validationFieldValue";

	public static final String ERROR_ATTRIBUTE_VALIDATION = "validationexception.error_attribute_validation";
	public static final String ERROR_ATTRIBUTE_NOT_FOUND = "validationexception.error_attribute_not_found";

	public static final String ERROR_LOGIN_LENGTH = "validationexception.error_login_length";
	public static final String ERROR_PASSWORD_LENGTH = "validationexception.error_password_length";

	public static final String ERROR_DOCUMENT_NAME_LENGTH = "validationexception.error_document_name_length";

	public static final String ERROR_PAGE_NAME_LENGTH = "validationexception.error_page_name_length";
	public static final String ERROR_PAGE_NAME_EXTENSION = "validationexception.error_page_name_extension";
	public static final String ERROR_PRIVATE_ORGS = "validationexception.error_private_orgs";

	public static final String ERROR_USERS_LIST_NOT_FOUND = "validationexception.error_users_list_not_found";

	public static final String ERROR_VALIDATION_DATA = "validationexception.error_validation_data";

	public static final String ERROR_GET_MATTER_FOR_OFFIC = "validationexception.error_get_matter_for_ofic";

	/**
	 * @param key
	 */
	public ValidationException(String key) {
		super(key);
	}

	/**
	 * @param exception
	 */
	public ValidationException(TecDocException exception) {
		super(exception);
	}

	/**
	 * @param key
	 * @param obj
	 */
	public ValidationException(String key, Object obj) {
		super(key, obj);
	}

	/**
	 * @param key
	 * @param objs
	 */
	public ValidationException(String key, Object[] objs) {
		super(key, objs);
	}

	/**
	 * @param key
	 * @param nested
	 */
	public ValidationException(String key, Throwable nested) {
		super(key, nested);
	}

	/**
	 * @param nested
	 */
	public ValidationException(Throwable nested) {
		super(nested);
	}

	/**
	 * @param key
	 * @param obj
	 * @param nested
	 */
	public ValidationException(String key, Object obj, Throwable nested) {
		super(key, obj, nested);
	}

	/**
	 * @param key
	 * @param objs
	 * @param nested
	 */
	public ValidationException(String key, Object[] objs, Throwable nested) {
		super(key, objs, nested);
	}

	protected String getNestedMessage(Locale locale) {
		String msg = null;
		if (key != null) {
			try {
				if (rb == null) {
					rb = ResourceBundle.getBundle(TECDOCEXCEPTION_FILE_NAME, locale);
				}
				msg = rb.getString(key);
				if (msg != null && objects != null) {

					if (key.equals(ERROR_ATTRIBUTE_VALIDATION)) {
						Object[] aux = new String[objects.length];
						for (int i = 0; i < aux.length; i++) {
							aux[i] = rb.getString((String) objects[i]);
						}
						msg = MessageFormat.format(msg, aux);
					} else {
						msg = MessageFormat.format(msg, objects);
					}
				}
			} catch (MissingResourceException mrE) {
				msg = null;
				log.fatal("No se puede encontrar el recurso" + TECDOCEXCEPTION_FILE_NAME, mrE);
			} catch (Exception e) {
				msg = null;
				log.fatal("No se puede encontrar el recurso" + TECDOCEXCEPTION_FILE_NAME, e);
			}
		}
		return msg;
	}
}
