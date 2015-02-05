package common.util.terceros;

import common.exceptions.CheckedArchivoException;

/**
 * Excepción lanzada cuando un número de identificación no es válido.
 */
public class IFNotValidException extends CheckedArchivoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Número de identificación. */
	private String ifNumber = null;

	/**
	 * Constructor.
	 */
	public IFNotValidException(String ifNumber) {
		super(ifNumber);
		this.ifNumber = ifNumber;
	}

	/**
	 * Obtiene el número de identificación.
	 * 
	 * @return Número de identificación.
	 */
	public String getIfNumber() {
		return ifNumber;
	}
}
