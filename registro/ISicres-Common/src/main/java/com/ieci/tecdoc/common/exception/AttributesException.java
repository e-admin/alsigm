//
// FileName: SessionException.java
//
package com.ieci.tecdoc.common.exception;

import java.io.Serializable;

/**
 * @author lmvicente
 * @version @since @creationDate 31-mar-2004
 */

public class AttributesException extends TecDocException implements Serializable {

	public static final String ERROR_CANNOT_FIND_VALIDATIONFIELDS = "attributesexception.cannot_find_validationfields";
	public static final String ERROR_NOT_VALIDATION_FIELD = "attributesexception.not_validation_field";
	public static final String ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS = "attributesexception.cannot_find_validationlistfields";
	public static final String ERROR_CANNOT_FIND_CITIES = "attributesexception.cannot_find_cities";
	public static final String ERROR_CANNOT_FIND_INTER = "attributesexception.cannot_find_inter";
	public static final String ERROR_CANNOT_FIND_PROV = "attributesexception.cannot_find_prov";
	public static final String ERROR_CANNOT_FIND_PERSON_ADDRESS = "attributesexception.cannot_find_person_address";
	public static final String ERROR_CANNOT_FIND_OTHER_OFFICE_USER = "attributesexception.cannot_find_other_office_user";
	public static final String ERROR_CANNOT_FIND_TYPE_DOCS = "attributesexception.cannot_find_type_docs";
	public static final String ERROR_CANNOT_FIND_TYPE_ADDRESSES = "attributesexception.cannot_find_type_addresses";
	
	
	/**
	 * @param key
	 */
	public AttributesException(String key) {
		super(key);
	}

	/**
	 * @param exception
	 */
	public AttributesException(TecDocException exception) {
		super(exception);
	}

	/**
	 * @param key
	 * @param obj
	 */
	public AttributesException(String key, Object obj) {
		super(key, obj);
	}

	/**
	 * @param key
	 * @param objs
	 */
	public AttributesException(String key, Object[] objs) {
		super(key, objs);
	}

	/**
	 * @param key
	 * @param nested
	 */
	public AttributesException(String key, Throwable nested) {
		super(key, nested);
	}

	/**
	 * @param nested
	 */
	public AttributesException(Throwable nested) {
		super(nested);
	}

	/**
	 * @param key
	 * @param obj
	 * @param nested
	 */
	public AttributesException(String key, Object obj, Throwable nested) {
		super(key, obj, nested);
	}

	/**
	 * @param key
	 * @param objs
	 * @param nested
	 */
	public AttributesException(String key, Object[] objs, Throwable nested) {
		super(key, objs, nested);
	}

}
