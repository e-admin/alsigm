//
// FileName: SessionException.java
//
package com.ieci.tecdoc.common.exception;

import java.io.Serializable;

/**
 * @author lmvicente
 * @version @since @creationDate 31-mar-2004
 */

public class SessionException extends TecDocException implements Serializable {

	public static final String ERROR_SESSION_EXPIRED = "sessionexception.error_session_expired";
	
	/**
	 * @param key
	 */
	public SessionException(String key) {
		super(key);
	}

	/**
	 * @param exception
	 */
	public SessionException(TecDocException exception) {
		super(exception);
	}

	/**
	 * @param key
	 * @param obj
	 */
	public SessionException(String key, Object obj) {
		super(key, obj);
	}

	/**
	 * @param key
	 * @param objs
	 */
	public SessionException(String key, Object[] objs) {
		super(key, objs);
	}

	/**
	 * @param key
	 * @param nested
	 */
	public SessionException(String key, Throwable nested) {
		super(key, nested);
	}

	/**
	 * @param nested
	 */
	public SessionException(Throwable nested) {
		super(nested);
	}

	/**
	 * @param key
	 * @param obj
	 * @param nested
	 */
	public SessionException(String key, Object obj, Throwable nested) {
		super(key, obj, nested);
	}

	/**
	 * @param key
	 * @param objs
	 * @param nested
	 */
	public SessionException(String key, Object[] objs, Throwable nested) {
		super(key, objs, nested);
	}

}
