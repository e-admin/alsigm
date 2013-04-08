package common.exceptions;

import common.Constants;

/**
 * Excepcion lanzada cuando se encuentran demasiados resultados.
 */
public class TooManyResultsException extends CheckedArchivoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Número de resultados. */
	private int count = 0;

	/** Número máximo de resultados. */
	private int maxNumResults = 0;

	/**
	 * Constructor.
	 * 
	 * @param count
	 *            Número de resultados.
	 */
	public TooManyResultsException(int count, int maxNumResults) {
		super(Constants.ERROR_TOO_MANY_RESULTS + ": " + count + "["
				+ maxNumResults + "]");
		this.count = count;
		this.maxNumResults = maxNumResults;
	}

	/**
	 * Obtiene el número de resultados.
	 * 
	 * @return Número de resultados.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Obtiene el número máximo de resultados.
	 * 
	 * @return Número máximo de resultados.
	 */
	public int getMaxNumResults() {
		return maxNumResults;
	}

}
