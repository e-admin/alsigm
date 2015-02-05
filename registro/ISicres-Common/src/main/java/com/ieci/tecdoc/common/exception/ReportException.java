//
// FileName: SecurityException.java
//
package com.ieci.tecdoc.common.exception;

import java.io.Serializable;

/**
 * @author lmvicente
 * @version @since @creationDate 24-mar-2004
 */

public class ReportException extends TecDocException implements Serializable {

    public static final String ERROR_CANNOT_FIND_REPORTS = "reportexception.cannot_find_reports";
    
    public static final String ERROR_CANNOT_GENERATE_REPORT = "reportexception.cannot_generate_report";
    
    public static final String ERROR_CANNOT_GET_TEMPLATEREPORT = "reportexception.cannot_get_template_report";
    
    public static final String ERROR_CANNOT_GENERATE_REPORT_INVALID_CODE = "reportexception.cannot_generate_report_invalid_code";
    
	/**
	 * @param key
	 */
	public ReportException(String key) {
		super(key);
	}

	/**
	 * @param exception
	 */
	public ReportException(TecDocException exception) {
		super(exception);
	}

	/**
	 * @param key
	 * @param obj
	 */
	public ReportException(String key, Object obj) {
		super(key, obj);
	}

	/**
	 * @param key
	 * @param objs
	 */
	public ReportException(String key, Object[] objs) {
		super(key, objs);
	}

	/**
	 * @param key
	 * @param nested
	 */
	public ReportException(String key, Throwable nested) {
		super(key, nested);
	}

	/**
	 * @param nested
	 */
	public ReportException(Throwable nested) {
		super(nested);
	}

	/**
	 * @param key
	 * @param obj
	 * @param nested
	 */
	public ReportException(String key, Object obj, Throwable nested) {
		super(key, obj, nested);
	}

	/**
	 * @param key
	 * @param objs
	 * @param nested
	 */
	public ReportException(String key, Object[] objs, Throwable nested) {
		super(key, objs, nested);
	}

}
