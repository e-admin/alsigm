/**
 * 
 */
package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Almacena un listado de libros de registro
 * 
 */
public class BooksInfo extends RetornoServicio {

	/**
	 * Lista de libros
	 */
	private BookInfo[] booksInfo;

	/**
	 * @return the booksInfo
	 */
	public BookInfo[] getBooksInfo() {
		return booksInfo;
	}

	/**
	 * @param booksInfo
	 *            the booksInfo to set
	 */
	public void setBooksInfo(BookInfo[] booksInfo) {
		this.booksInfo = booksInfo;
	}

}
