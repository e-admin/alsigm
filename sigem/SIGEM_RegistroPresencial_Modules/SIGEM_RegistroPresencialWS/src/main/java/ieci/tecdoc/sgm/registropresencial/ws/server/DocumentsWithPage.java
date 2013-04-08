package ieci.tecdoc.sgm.registropresencial.ws.server;

/**
 * 
 * Tipo general de documento. Un documento almacena muchas páginas y cada una de
 * ellas un fichero
 * 
 */
public class DocumentsWithPage {

	/**
	 * Nombre del documento
	 */
	private String documentName = null;
	
	/**
	 * Identificador del libro de registro
	 */
	private String bookId = null;
	
	/**
	 * Identificador del registro
	 */
	private String folderId = null;
	
	/**
	 * Identificador del documento
	 */
	private String docID = null;
	
	/**
	 * Lista de páginas
	 */
	private Page pages[] = null;
	

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
	public Page[] getPages() {
		return pages;
	}

	/**
	 * @param pages
	 */
	public void setPages(Page[] pages) {
		this.pages = pages;
	}
}
