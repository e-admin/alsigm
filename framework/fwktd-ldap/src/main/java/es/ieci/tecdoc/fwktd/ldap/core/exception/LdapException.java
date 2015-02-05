package es.ieci.tecdoc.fwktd.ldap.core.exception;


/**
 * Excepcion base LDAP
 * @author Iecisa
 * @version $Revision: 78 $
 *
 */
public class LdapException extends RuntimeException{

	/**
	 * Numero de serie por defecto
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Codigo de error de la excepcion
	 */
	private String errCode;

	/**
	 * Mensaje de error
	 */
	private String errMessage;

	/**
	 * Constructor a partir del codigo de error y el mensaje
	 * @param errCode Codigo de error
	 * @param errMessage Mensaje de texto del error
	 */
	public LdapException(final String errCode, final String errMessage) {
		super(errMessage);
		this.errCode = errCode;
		this.errMessage = errMessage;
	}

	/**
	 * Constructor a partir del codigo de error y la causa
	 * @param errCode Codigo de error
	 * @param errMessage Mensaje de texto del error
	 * @param exception Excepcion original
	 */
	public LdapException(final String errCode, final String errMessage, final Exception exception) {
		super(errMessage, exception.getCause());
		this.setStackTrace(exception.getStackTrace());
		this.errCode = errCode;
		this.errMessage = errMessage;
	}

	/**
	 * Permite obtener el codigo de error
	 * @return Codigo de error de la excepcion
	 */
	public String getErrCode() {
		return errCode;
	}

	/**
	 * Permite obtener el mensaje de error
	 * @return mensaje de error
	 */
	public String getErrMessage() {
		return errMessage;
	}

}
