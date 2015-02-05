/**
 * 
 */
package com.ieci.tecdoc.common.repository.vo;

/**
 * Clase que contiene los datos comunes
 * 
 * @author Iecisa
 * 
 */

public class ISRepositoryBasicDocumentVO {

	/**
	 * Identificador del libro de registro
	 */
	protected Integer bookID;

	/**
	 * Identificador del tipo de libro
	 */
	protected Integer bookType;

	/**
	 * Nombre del registro
	 */
	protected String bookName;

	/**
	 * Identificador del Registro
	 */
	protected Integer fdrid;

	/**
	 * Entidad con la que trabajamos
	 */
	protected String entidad;

	/**
	 * Contenido del fichero
	 */
	protected byte[] fileContent;

	/**
	 * @return the bookID
	 */
	public Integer getBookID() {
		return bookID;
	}

	/**
	 * @param bookID
	 *            the bookID to set
	 */
	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}

	/**
	 * @return the bookType
	 */
	public Integer getBookType() {
		return bookType;
	}

	/**
	 * @param bookType
	 *            the bookType to set
	 */
	public void setBookType(Integer bookType) {
		this.bookType = bookType;
	}

	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * @param bookName
	 *            the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	/**
	 * @return the fdrid
	 */
	public Integer getFdrid() {
		return fdrid;
	}

	/**
	 * @param fdrid
	 *            the fdrid to set
	 */
	public void setFdrid(Integer fdrid) {
		this.fdrid = fdrid;
	}

	/**
	 * @return the entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the fileContent
	 */
	public byte[] getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent
	 *            the fileContent to set
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

}
