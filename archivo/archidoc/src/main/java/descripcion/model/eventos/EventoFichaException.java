package descripcion.model.eventos;

import common.exceptions.CheckedArchivoException;

/**
 * Excepción lanzada cuando ocurre algún error en la ejecución del evento.
 */
public class EventoFichaException extends CheckedArchivoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public EventoFichaException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param e
	 *            Excepción capturada.
	 */
	public EventoFichaException(Throwable e) {
		super(e);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            Causa de la excepción.
	 * @param cause
	 *            Causa de la excepción.
	 */
	public EventoFichaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public EventoFichaException(String cause) {
		super(cause);
	}

}
