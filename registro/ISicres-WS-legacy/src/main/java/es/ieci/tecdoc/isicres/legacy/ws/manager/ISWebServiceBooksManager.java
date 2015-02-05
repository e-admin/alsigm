package es.ieci.tecdoc.isicres.legacy.ws.manager;


import es.ieci.tecdoc.isicres.ws.legacy.service.books.ArrayOfWSBook;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.ArrayOfWSField;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.WSBook;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.WSField;

public interface ISWebServiceBooksManager {

	/**
	 * Metodo que obtiene el esquema del libro mediante el idBook y security
	 * @param bookIdentification 
	 * @param security
	 * @return ArrayOfWSField es un array de {@link WSField}
	 */
	public ArrayOfWSField getBookSchema(int bookIdentification, Security security);
	
	/**
	 * Metodo que obtiene los libros que son de entrada
	 * @param security
	 * @return ArrayOfWSField es un array de {@link WSBook}
	 */
	public ArrayOfWSBook getInputBooks(Security security);
	
	/**
	 * Metodo que obtiene los libros que son de salida
	 * 
	 * @param security
	 * @return ArrayOfWSField es un array de {@link WSBook}
	 */
	public ArrayOfWSBook getOutputBooks(Security security);

}