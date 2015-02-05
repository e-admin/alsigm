/**
 *
 */
package com.ieci.tecdoc.common.compulsa.vo;

import java.util.Locale;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 */
public class ISicresBaseCompulsaVO {

	protected Integer bookId;
	protected Integer folderId;
	protected String sessionID;
	protected Locale locale;
	protected String entidad;

	protected String locator;

	/**
	 * @return el bookId
	 */
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 *            el bookId a fijar
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return el folderId
	 */
	public Integer getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId
	 *            el folderId a fijar
	 */
	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	/**
	 * @return el sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * @param sessionID
	 *            el sessionID a fijar
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * @return el locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            el locale a fijar
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @return el entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            el entidad a fijar
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return el locator
	 */
	public String getLocator() {
		return locator;
	}

	/**
	 * @param locator
	 *            el locator a fijar
	 */
	public void setLocator(String locator) {
		this.locator = locator;
	}

}
