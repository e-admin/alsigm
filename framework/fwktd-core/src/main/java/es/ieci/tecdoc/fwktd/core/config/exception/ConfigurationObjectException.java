package es.ieci.tecdoc.fwktd.core.config.exception;

/**
 * Clase base para excepciones de configuracion
 */
public class ConfigurationObjectException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Código de error de la excepción
	 */
	private String errCode;

	/**
	 * Constructor a partir del código de error
	 * @param errCode Código de error
	 */
	public ConfigurationObjectException(String errCode) {
		super();
		
		this.errCode = errCode;
	}
	
	/**
	 * Constructor a partir del código de error y el mensaje
	 * @param errCode Código de error
	 * @param message Mensaje de la excepcion
	 */
	public ConfigurationObjectException(String errCode, String message) {
		super(message);
		this.errCode = errCode;
	}

	/**
	 * Constructor a partir del código de error y la causa
	 * @param errCode Código de error
	 * @param cause Causa de la excepción
	 */
	public ConfigurationObjectException(String errCode, Throwable cause) {
		super(cause);
		
		this.errCode = errCode;
	}
	
	/**
	 * Constructor a partir del código de error, el mensaje y la causa
	 * @param errCode Código de error
	 * @param message Mensaje de la excepcion
	 * @param cause Causa de la excepción
	 */
	public ConfigurationObjectException(String errCode, String message, Throwable cause) {
		super(message, cause);
		
		this.errCode = errCode;
	}
	
	/**
	 * Permite obtener el código de error
	 * @return Código de error de la excepción
	 */
	public String getErrCode() {
		return errCode;
	}
	
}
