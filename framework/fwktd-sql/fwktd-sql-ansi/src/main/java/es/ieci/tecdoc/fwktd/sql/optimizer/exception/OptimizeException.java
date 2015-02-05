package es.ieci.tecdoc.fwktd.sql.optimizer.exception;

import es.ieci.tecdoc.fwktd.core.exception.ApplicationException;
import es.ieci.tecdoc.fwktd.core.model.RemoteMessageVO;

/**
 * 
 * Excepciones en el proceso de optimización de IStatements.
 * 
 */
public class OptimizeException extends ApplicationException {

	public OptimizeException(LevelEnum aLevel, RemoteMessageVO aRemoteError) {
		super(aLevel, aRemoteError);
	}

	public OptimizeException(LevelEnum aLevel, String aMessageId,
			String[] aParams, String aDefaultMessage) {
		super(aLevel, aMessageId, aParams, aDefaultMessage);
	}

	public OptimizeException(LevelEnum aLevel, String aMsg) {
		super(aLevel, aMsg);
	}

	public OptimizeException(LevelEnum aLevel, String[] aParams,
			String aDefaultMessage) {
		super(aLevel, aParams, aDefaultMessage);
	}

	public OptimizeException(RemoteMessageVO aRemoteError) {
		super(aRemoteError);
	}

	public OptimizeException(String aMessageId, String[] aParams,
			String aDefaultMessage) {
		super(aMessageId, aParams, aDefaultMessage);
	}

	
	public OptimizeException(String aMsg) {
		super(aMsg);
	}

	public OptimizeException(String[] aParams, String aDefaultMessage) {
		super(aParams, aDefaultMessage);
	}

	@Override
	public String getDefaultMessageId() {
		return "error.sql.optimize";
	}

	// Members

	private static final long serialVersionUID = 8082346368693230822L;
}
