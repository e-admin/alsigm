/**
 * 
 */
package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Criterios de busqueda de registros
 * 
 */
public class FolderSearchCriteria extends RetornoServicio {

	/**
	 * Identificador del registro
	 */
	private String folderId;

	/**
	 * Número de registro
	 */
	private String folderNumber;

	/**
	 * Listado de criterios de busqueda
	 */
	private FieldsSearchCriteria fields;

	/**
	 * Identificador del libro de registro
	 */
	private BookId bookId;

	/**
	 * @return
	 */
	public String getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId
	 */
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	/**
	 * @return
	 */
	public String getFolderNumber() {
		return folderNumber;
	}

	/**
	 * @param folderNumber
	 */
	public void setFolderNumber(String folderNumber) {
		this.folderNumber = folderNumber;
	}

	/**
	 * @return
	 */
	public FieldsSearchCriteria getFields() {
		return fields;
	}

	/**
	 * @param fields
	 */
	public void setFields(FieldsSearchCriteria fields) {
		this.fields = fields;
	}

	/**
	 * @return
	 */
	public BookId getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 */
	public void setBookId(BookId bookId) {
		this.bookId = bookId;
	}

}
