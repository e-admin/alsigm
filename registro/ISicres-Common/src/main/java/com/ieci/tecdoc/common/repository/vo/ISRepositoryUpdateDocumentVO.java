/**
 * 
 */
package com.ieci.tecdoc.common.repository.vo;

/**
 * VO que contiene todos los valores necesarios para actualizar un documento en
 * el gestor documental
 * 
 * @author Iecisa
 * 
 */

public class ISRepositoryUpdateDocumentVO extends
		ISRepositoryBasicDocumentVO {

	/**
	 * Identificador del documento en el gestor documental
	 */
	protected String documentUID;

	/**
	 * Nombre del fichero
	 */
	protected String fileName;

	/**
	 * Extension del fichero
	 */
	protected String fileExtension;

	/**
	 * Identificador del documento de registros
	 */
	protected Integer docId;

	/**
	 * Identificador del formato de invesdoc
	 */
	protected int miscFormatId;

	/**
	 * @return the documentUID
	 */
	public String getDocumentUID() {
		return documentUID;
	}

	/**
	 * @param documentUID
	 *            the documentUID to set
	 */
	public void setDocumentUID(String documentUID) {
		this.documentUID = documentUID;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileExtension
	 */
	public String getFileExtension() {
		return fileExtension;
	}

	/**
	 * @param fileExtension
	 *            the fileExtension to set
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

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

}
