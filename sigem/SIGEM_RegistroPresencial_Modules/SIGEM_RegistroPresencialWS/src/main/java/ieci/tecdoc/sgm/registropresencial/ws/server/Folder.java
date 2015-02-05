package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Información completa de un registro
 * 
 */
public class Folder extends RetornoServicio {

	/**
	 * Identificador del registro
	 */
	private String folderId;

	/**
	 * . Número de registro
	 */
	private String folderNumber;

	/**
	 * Lista de los datos de un registro
	 */
	private Fields fields;

	/**
	 * Identificador del libro de registro.
	 */
	private BookId bookId;

	/**
	 * Listado de documentos con varias páginas
	 */
	private DocumentsWithPage docWithPage[];

	/**
	 * Lista de documentos
	 */
	private Documents documentos;

	/**
	 * @return
	 */
	public Documents getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos
	 */
	public void setDocumentos(Documents documentos) {
		this.documentos = documentos;
	}

	/**
	 * @return
	 */
	public Fields getFields() {
		return fields;
	}

	/**
	 * @param fields
	 */
	public void setFields(Fields fields) {
		this.fields = fields;
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
	public BookId getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 */
	public void setBookId(BookId bookId) {
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
	public DocumentsWithPage[] getDocWithPage() {
		return docWithPage;
	}

	/**
	 * @param docWithPage
	 */
	public void setDocWithPage(DocumentsWithPage[] docWithPage) {
		this.docWithPage = docWithPage;
	}
}
