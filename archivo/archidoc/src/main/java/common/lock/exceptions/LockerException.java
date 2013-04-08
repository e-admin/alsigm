package common.lock.exceptions;

import common.Constants;
import common.exceptions.CheckedArchivoException;
import common.lock.vos.LockVO;

/**
 * Excepción lanzada cuando el objeto está bloqueado.
 */
public class LockerException extends CheckedArchivoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Información del bloqueo. */
	LockVO lockInfo = null;

	/**
	 * Constructor.
	 * 
	 * @param lockInfo
	 *            Información del bloqueo.
	 */
	public LockerException(LockVO lockInfo) {
		super();

		this.lockInfo = lockInfo;
	}

	/**
	 * Obtiene la información del bloqueo.
	 * 
	 * @return Información del bloqueo.
	 */
	public LockVO getLockVO() {
		return lockInfo;
	}

	/**
	 * Obtiene una representación del objeto.
	 * 
	 * @return Representación del objeto.
	 */
	public String getMessage() {
		return new StringBuffer().append("<LockerException>")
				.append(Constants.NEWLINE)
				.append(lockInfo != null ? lockInfo.toXML(2) : "")
				.append("</LockerException>").append(Constants.NEWLINE)
				.toString();
	}
}
