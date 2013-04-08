package ieci.tdw.ispac.designer.client.exceptions;

import com.google.gwt.user.client.rpc.SerializableException;

/**
 * Excepción lanzada si ocurre algún error en el diseñador gráfico.
 *
 */
public class DesignerException extends SerializableException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor.
	 */
	public DesignerException() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param message Mensaje de la excepción.
	 */
	public DesignerException(String message) {
		super(message);
	}
	
}
