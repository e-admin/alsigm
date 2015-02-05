/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.exception;

import es.ieci.tecdoc.fwktd.core.exception.ApplicationException;

/**
 * Excepción que se lanza si ocurre un error durante el parseo de SQL.
 * 
 * @author IECISA
 */
public class SQLParserException extends ApplicationException {

	public SQLParserException(String messageId, String[] params,
			String defaultMessage) {
		super(messageId, params, defaultMessage);
	}

	public String getDefaultMessageId() {
		return "error.global.sql.parsing";
	}

	// Members
	private static final long serialVersionUID = 617854437790175814L;

}
