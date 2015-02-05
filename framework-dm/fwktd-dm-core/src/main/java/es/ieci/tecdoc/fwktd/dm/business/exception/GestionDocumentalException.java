package es.ieci.tecdoc.fwktd.dm.business.exception;

/**
 * Excepción base del módulo de gestión documental.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class GestionDocumentalException extends Exception {

	/**
	 * UID de versión.
	 */
	private static final long serialVersionUID = -5733720301390437612L;


	/**
	 * Constructor.
	 */
	public GestionDocumentalException() {
		super();
	}

	/**
	 * Constructor.
	 */
	public GestionDocumentalException(String mensaje) {
		super(mensaje);
	}

	/**
	 * Constructor.
	 */
	public GestionDocumentalException(Throwable causa) {
		super(causa);
	}

	/**
	 * Constructor.
	 */
	public GestionDocumentalException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

}
