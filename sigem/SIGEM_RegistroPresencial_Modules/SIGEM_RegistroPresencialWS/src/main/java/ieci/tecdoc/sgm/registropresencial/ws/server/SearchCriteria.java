package ieci.tecdoc.sgm.registropresencial.ws.server;

/**
 * Criterio de búsqueda, indice del primer registro a obtener y número máximo de
 * registros que devolver
 * 
 */
public class SearchCriteria {

	/**
	 * Estado de la distribución.
	 * 
	 * @value: 1-Pendiente.
	 * @value: 2-Aceptado.
	 * @value: 3-Archivado.
	 * @value: 4-Rechazado.
	 * @value: 5-Redistribuido.
	 */
	protected String state;

	/**
	 * Primera fila a devolver
	 */
	protected String firstRow;

	/**
	 * Número máximo de resultados a devolver
	 */
	protected String maxResults;

	/**
	 * Tipo del archivador o libro sobre los que buscaremos los registros
	 * distribuidos.
	 * 
	 * @value: 0-Tanto para archivadores o libros de entrada como de salida.
	 * @value: 1-Archivadores o libros de entrada.
	 * @value: 2-Archivadores o libros de salida.
	 */
	protected String typeBookRegister;

	/**
	 * Este parámetro indica si se buscan los registros distribuidos al
	 * departamento al que pertenece el usuario (cuando vale false o 0) o a este
	 * y a los que esta agregado (cuando vale true o 1).
	 */
	protected String oficAsoc;

	/**
	 * Este parametro infica si se busca solamente en los libros abiertos o en
	 * todos los libros
	 */
	protected String onlyOpenBooks;

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the firstRow
	 */
	public String getFirstRow() {
		return firstRow;
	}

	/**
	 * @param firstRow
	 *            the firstRow to set
	 */
	public void setFirstRow(String firstRow) {
		this.firstRow = firstRow;
	}

	/**
	 * @return the maxResults
	 */
	public String getMaxResults() {
		return maxResults;
	}

	/**
	 * @param maxResults
	 *            the maxResults to set
	 */
	public void setMaxResults(String maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * @return the typeBookRegister
	 */
	public String getTypeBookRegister() {
		return typeBookRegister;
	}

	/**
	 * @param typeBookRegister
	 *            the typeBookRegister to set
	 */
	public void setTypeBookRegister(String typeBookRegister) {
		this.typeBookRegister = typeBookRegister;
	}

	/**
	 * @return the oficAsoc
	 */
	public String getOficAsoc() {
		return oficAsoc;
	}

	/**
	 * @param oficAsoc
	 *            the oficAsoc to set
	 */
	public void setOficAsoc(String oficAsoc) {
		this.oficAsoc = oficAsoc;
	}

	/**
	 * @return the onlyOpenBooks
	 */
	public String getOnlyOpenBooks() {
		return onlyOpenBooks;
	}

	/**
	 * @param onlyOpenBooks
	 *            the onlyOpenBooks to set
	 */
	public void setOnlyOpenBooks(String onlyOpenBooks) {
		this.onlyOpenBooks = onlyOpenBooks;
	}

}
