/*
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceBooksManager;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.ArrayOfWSBook;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.ArrayOfWSField;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.ISWebServiceBooksSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.Security;

/**
 * 
 * @author IECISA
 * 
 */
public class ISWebServiceBooksSoapImpl implements ISWebServiceBooksSoap {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.ws.legacy.service.books.ISWebServiceBooksSoap#
	 * wsGetBookSchema(int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.books.Security)
	 */
	public ArrayOfWSField wsGetBookSchema(int bookIdentification,
			Security security) {

		return getIsWebServiceBooksManager().getBookSchema(bookIdentification,
				security);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.ws.legacy.service.books.ISWebServiceBooksSoap#
	 * wsGetInputBooks(es.ieci.tecdoc.isicres.ws.legacy.service.books.Security)
	 */
	public ArrayOfWSBook wsGetInputBooks(Security security) {

		return getIsWebServiceBooksManager().getInputBooks(security);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.ws.legacy.service.books.ISWebServiceBooksSoap#
	 * wsGetOutputBooks(es.ieci.tecdoc.isicres.ws.legacy.service.books.Security)
	 */
	public ArrayOfWSBook wsGetOutputBooks(Security security) {

		return getIsWebServiceBooksManager().getOutputBooks(security);
	}

	public ISWebServiceBooksManager getIsWebServiceBooksManager() {
		return isWebServiceBooksManager;
	}

	public void setIsWebServiceBooksManager(
			ISWebServiceBooksManager isWebServiceBooksManager) {
		this.isWebServiceBooksManager = isWebServiceBooksManager;
	}

	// Members
	protected ISWebServiceBooksManager isWebServiceBooksManager;
}
