/**
 * 
 */
package ieci.tecdoc.sgm.core.services.gestioncsv;

import ieci.tecdoc.sgm.core.exception.SigemException;

/**
 * @author IECISA
 * 
 *         Excepción del servicio de gestión de CSV de SIGEM.
 * 
 *         Se lanza cuando ocurre algún error en los métodos del módulo.
 * 
 */
public class CSVException extends SigemException {

	/**
	 * @param errorCode
	 */
	public CSVException(long errorCode) {
		super(errorCode);
	}

	public CSVException(String message, Throwable e) {
		super(message, e);
	}

	public CSVException(long errorCode, Throwable e) {
		super(errorCode, e);
	}

	public CSVException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6663624065932057052L;

}
