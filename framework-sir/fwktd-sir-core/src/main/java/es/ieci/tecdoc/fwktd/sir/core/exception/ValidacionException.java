package es.ieci.tecdoc.fwktd.sir.core.exception;

import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;

public class ValidacionException extends SIRException {

	private static final long serialVersionUID = 3711317582888628132L;

	/**
	 * Código de error de validación.
	 */
	private ErroresEnum errorValidacion = null;

	/**
	 * Excepción que ha causado el error.
	 */
	private Throwable errorException = null;

	/**
	 * Constructor.
	 * @param error Error de validación.
	 */
	public ValidacionException(ErroresEnum error) {
		super("error.sir." + error.getValue(), null, error.getName());
		setErrorValidacion(error);
	}

	/**
	 * Constructor.
	 * @param error Error de validación.
	 */
	public ValidacionException(ErroresEnum error, Throwable errorException) {
		super("error.sir." + error.getValue(), null, error.getName());
		setErrorValidacion(error);
		setErrorException(errorException);
	}

	public ErroresEnum getErrorValidacion() {
		return errorValidacion;
	}

	public void setErrorValidacion(ErroresEnum errorValidacion) {
		this.errorValidacion = errorValidacion;
	}

	public Throwable getErrorException() {
		return errorException;
	}

	public void setErrorException(Throwable errorException) {
		this.errorException = errorException;
	}

	/**
	 * Obtiene el id del mensaje por defecto. Se utiliza para indicar la clave,
	 * en un fichero de recursos, de la que obtener el mensaje
	 * internacionalizado de la excepción.
	 *
	 * @return el id del mensaje internacionalizado de la excepción
	 */
	@Override
	public String getDefaultMessageId() {
		return "error.sir.validacion";
	}

}
