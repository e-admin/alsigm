/**
 *
 */
package com.ieci.tecdoc.common.repository.vo;

/**
 * VO que contiene todos los valores necesarios para recuperar un documento del
 * gestor documental
 *
 * @author Iecisa
 *
 */

public class ISRepositoryRetrieveDocumentVO extends ISRepositoryBasicDocumentVO {

	/**
	 * Identificador del pagina del documento en el registro
	 */
	protected Integer pageID;

	/**
	 * Identificador del documento en el gestor documental
	 */
	protected String documentUID;

	/**
	 * @return the pageID
	 */
	public Integer getPageID() {
		return pageID;
	}

	/**
	 * @param pageID
	 *            the pageID to set
	 */
	public void setPageID(Integer pageID) {
		this.pageID = pageID;
	}

	/**
	 * @return el documentUID
	 */
	public String getDocumentUID() {
		return documentUID;
	}

	/**
	 * @param documentUID
	 *            el documentUID a fijar
	 */
	public void setDocumentUID(String documentUID) {
		this.documentUID = documentUID;
	}

}
