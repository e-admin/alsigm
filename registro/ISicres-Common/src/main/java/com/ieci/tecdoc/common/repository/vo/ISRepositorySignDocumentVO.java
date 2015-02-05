/**
 * 
 */
package com.ieci.tecdoc.common.repository.vo;

import com.ieci.tecdoc.common.conf.BookConf;
import com.ieci.tecdoc.common.conf.BookTypeConf;

/**
 * VO que contiene todos los datos necesarios para que se realize la firma de un
 * domento. Ya sea un documento almacenado en el gestor documental o no
 * 
 * @author Iecisa
 * 
 * 
 */

public class ISRepositorySignDocumentVO extends
		ISRepositoryBasicDocumentVO {

	/**
	 * Identificador de la carpeta de documentos del registro
	 */
	protected Integer docId;

	/**
	 * Identificador del pagina del documento en el registro
	 */
	protected Integer pageID;

	/**
	 * Identificador del formato de invesdoc
	 */
	protected int miscFormatId;

	/**
	 * Configuración del tipo de libro
	 */
	protected BookTypeConf bookTypeConf;

	/**
	 * Configuración del libro
	 */
	protected BookConf bookConf;

	/**
	 * @return the docId
	 */
	public Integer getDocId() {
		return docId;
	}

	/**
	 * @param docId
	 *            the docId to set
	 */
	public void setDocId(Integer docId) {
		this.docId = docId;
	}

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
	 * @return the miscFormatId
	 */
	public int getMiscFormatId() {
		return miscFormatId;
	}

	/**
	 * @param miscFormatId
	 *            the miscFormatId to set
	 */
	public void setMiscFormatId(int miscFormatId) {
		this.miscFormatId = miscFormatId;
	}

	/**
	 * @return the bookTypeConf
	 */
	public BookTypeConf getBookTypeConf() {
		return bookTypeConf;
	}

	/**
	 * @param bookTypeConf
	 *            the bookTypeConf to set
	 */
	public void setBookTypeConf(BookTypeConf bookTypeConf) {
		this.bookTypeConf = bookTypeConf;
	}

	/**
	 * @return el bookConf
	 */
	public BookConf getBookConf() {
		return bookConf;
	}

	/**
	 * @param bookConf el bookConf a fijar
	 */
	public void setBookConf(BookConf bookConf) {
		this.bookConf = bookConf;
	}
	
	

}
