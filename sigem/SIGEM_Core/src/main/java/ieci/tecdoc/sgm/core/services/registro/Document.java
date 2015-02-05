package ieci.tecdoc.sgm.core.services.registro;

import java.util.ArrayList;
import java.util.List;

/**
 * Información de un documento
 * 
 * 
 */
public class Document {

	/**
	 * Nombre del documento
	 */
	protected String documentName = null;

	/**
	 * Libro al que pertenece el registro al que está asociado el documento
	 */
	protected String bookId = null;

	/**
	 * Registro al que esta asociado el documento
	 */
	protected String folderId = null;

	/**
	 * Identificador del documento
	 */
	protected String docID = null;

	/**
	 * Lista de páginas
	 */
	protected List pages = new ArrayList();

	/**
	 * @return
	 */
	public String getDocID() {
		return docID;
	}

	/**
	 * @param docID
	 */
	public void setDocID(String docID) {
		this.docID = docID;
	}

	/**
	 * @return
	 */
	public String getDocumentName() {
		return documentName;
	}

	/**
	 * @param documentName
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * @return
	 */
	public String getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

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
	public List getPages() {
		return pages;
	}

	/**
	 * @param pages
	 */
	public void setPages(List pages) {
		this.pages = pages;
	}

	/**
	 * @param page
	 */
	public void addPage(Page page) {
		if (page != null) {
			pages.add(page);
		}
	}

}
