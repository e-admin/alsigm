//
// FileName: TecDocException.java
//
package com.ieci.tecdoc.common.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @author lmvicente
 * @version @since  @creationDate 24-mar-2004
 */

public class TecDocException extends Exception implements Serializable {

	/***************************************************************************************************************************************
	 * Attributes
	 **************************************************************************************************************************************/

	private static Logger log = Logger.getLogger(TecDocException.class);

	public static final String ERROR_AUTHENTICATION_POLICY_NOTFOUND = "tecdocexception.error_authentication_policy_notfound";
	public static final String ERROR_REPOSITORY_POLICY_NOTFOUND = "tecdocexception.error_repository_policy_notfound";
	public static final String ERROR_COMPULSA_POLICY_NOTFOUND = "tecdocexception.error_compulsa_policy_notfound";
	public static final String ERROR_CACHE_NOTFOUND = "tecdocexception.error_cache_notfound";

	protected static final String TECDOCEXCEPTION_FILE_NAME = "resources/TecDocException";

	protected Throwable nested = null;
	protected String key = null;
	protected Object[] objects = null;

	protected ResourceBundle rb = null;

	/***************************************************************************************************************************************
	 * Constructors
	 **************************************************************************************************************************************/

	public TecDocException(String key) {
		this(key, null, null);
	}

	public TecDocException(TecDocException exception) {
		this(exception.getCode(), exception.getObjects(), null);
	}

	public TecDocException(String key, Object obj) {
		this(key, new Object[] { obj });
	}

	public TecDocException(String key, Object[] objs) {
		this(key, objs, null);
	}

	public TecDocException(String key, Throwable nested) {
		this(key, null, nested);
	}

	public TecDocException(Throwable nested) {
		this(null, null, nested);
	}

	public TecDocException(String key, Object obj, Throwable nested) {
		this(key, new Object[] { obj }, nested);
	}

	public TecDocException(String key, Object[] objs, Throwable nested) {
		this.nested = nested;
		this.key = key;
		this.objects = objs;
	}

	/***************************************************************************************************************************************
	 * Public methods
	 **************************************************************************************************************************************/

	/**
	 * @return Returns the nested.
	 */
	public Throwable getNested() {
		return nested;
	}

	/**
	 * @param nested
	 *            The nested to set.
	 */
	public void setNested(Throwable nested) {
		this.nested = nested;
	}

	/**
	 * @return Returns the objects.
	 */
	public Object[] getObjects() {
		return objects;
	}

	/**
	 * @param objects
	 *            The objects to set.
	 */
	public void setObjects(Object[] objects) {
		this.objects = objects;
	}

	public String getCode() {
		return key;
	}

	public void setCode(String code) {
		this.key = code;
	}

	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		return getMessage(Locale.getDefault());
	}

	public String getMessage(Locale locale) {
		if (nested == null)
			return getNestedMessage(locale);
		else {
			StringBuffer sbf = new StringBuffer();
			sbf.append(getNestedMessage(locale));
			sbf.append(" {\n  Nested Exception: ");
			sbf.append(nested.getMessage());
			sbf.append("\n}");
			return sbf.toString();
		}
	}

	public String getLocalizedMessage() {
		return getLocalizedMessage(Locale.getDefault());
	}

	public String getLocalizedMessage(Locale locale) {
		return getMessage(locale);
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return toString(Locale.getDefault());
	}

	public String toString(Locale locale) {
		StringBuffer sbf = new StringBuffer();
		sbf.append(getClass().getName());
		sbf.append(":");
		sbf.append(getNestedMessage(locale));

		if (nested != null) {
			sbf.append(" {\n Nested Exception: ");
			sbf.append(nested.toString());
			sbf.append("\n}");
		}

		return sbf.toString();
	}

	/**
	 * @see java.lang.Throwable#printStackTrace(PrintWriter)
	 */
	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);
		if (nested != null) {
			s.println("NESTED EXCEPTION:");
			nested.printStackTrace(s);
		}
	}

	/**
	 * @see java.lang.Throwable#printStackTrace(PrintStream)
	 */
	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);
		if (nested != null) {
			s.println("NESTED EXCEPTION:");
			nested.printStackTrace(s);
		}
	}

	/**
	 * @see java.lang.Throwable#printStackTrace()
	 */
	public void printStackTrace() {
		super.printStackTrace();
		if (nested != null) {
			System.err.println("NESTED EXCEPTION:");
			nested.printStackTrace();
		}
	}

	/***************************************************************************************************************************************
	 * Protected methods
	 **************************************************************************************************************************************/

	protected String getNestedMessage(Locale locale) {
		String msg = null;
		if (key != null) {
			try {
				//if (rb == null) {
					rb = ResourceBundle.getBundle(TECDOCEXCEPTION_FILE_NAME, locale);
				//}
				msg = rb.getString(key);
				if (msg != null && objects != null) {
					msg = MessageFormat.format(msg, objects);
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

	/***************************************************************************************************************************************
	 * Private methods
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Inner classes
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Test brench
	 **************************************************************************************************************************************/

}
