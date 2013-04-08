/**
 * 
 */
package es.ieci.tecdoc.fwktd.core.exception;

import org.springframework.validation.ObjectError;

import es.ieci.tecdoc.fwktd.core.model.RemoteMessageVO;
import es.ieci.tecdoc.fwktd.util.ErrorUtils;

/*
 * 
 */


/**
 * Excepcion específica para servicios remotos pueden ser serializadas e
 * internacionalizadas en clientes.
 * 
 * @see RemoteExceptionWrapperAdvice
 * @see RemoteMessageVO
 * 
 */
public class RemoteException extends FrameworkException implements
		ManageableException {

	/**
	 * Version
	 */
	private static final long serialVersionUID = 8988050033721897967L;

	/**
	 * Nivel de error de la excepción. Por defecto a ERROR.
	 */
	protected LevelEnum level = LevelEnum.ERROR;

	/**
	 * Error
	 */
	protected ObjectError error;

	/**
	 * Constructor.
	 * 
	 * @param msg
	 *            Mensaje de error
	 */
	protected RemoteException(String msg) {
		this(LevelEnum.ERROR, msg);
	}

	/**
	 * Constructor.
	 * 
	 * @param level
	 *            Nivel del mensaje de error
	 * @param msg
	 *            Mensaje de error
	 */
	protected RemoteException(LevelEnum level, String msg) {
		super(msg);
		this.level = level;
	}

	/**
	 * Constructor para reemplazar la Excepción por una Remote. No se envuelve.
	 * 
	 * @param msg
	 *            Mensaje asociado.
	 * @param e
	 *            Excepcion lanzada.
	 */
	public RemoteException(String msg, Exception e) {
		this(ErrorUtils.getLevel(e), msg, e);
	}

	/**
	 * Constructor para reemplazar la Excepción por una Remote. No se envuelve.
	 * 
	 * @param level
	 *            nivel de error
	 * @param msg
	 *            Mensaje asociado.
	 * @param e
	 *            Excepcion lanzada.
	 */
	public RemoteException(LevelEnum level, String msg, Exception e) {
		this(level, msg);
		this.error = ErrorUtils.getObjectError(e);
	}

	public ObjectError getError() {
		return error;
	}

	/**
	 * getter RemoteMessageVO
	 * 
	 * @return RemoteMessageVO
	 */
	public RemoteMessageVO getRemoteError() {
		return new RemoteMessageVO(getError());
	}

	public LevelEnum getLevel() {
		return level;
	}

}
