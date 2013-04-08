package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * 
 * Tipo particular de documento (Un documento formado por una página que
 * almacena un fichero)
 * 
 */
public class Document extends RetornoServicio {

	/**
	 * Nombre del documento (valor lógico).
	 */
	private String documentName = null;
	
	/**
	 * Nombre del fichero (valor físico).
	 */
	private String fileName = null;
	
	/**
	 * Nombre de la página (valor lógico).
	 */
	private String pageName = null;
	
	/**
	 * Contenido físico del documento en base 64.
	 */
	private String documentContentB64 = null;
	
	/**
	 * Extensión del fichero.
	 */
	private String extension = null;
	
	/**
	 * Objeto de tipo Folder con información sobre el registro donde se encuentra el documento.
	 */
	private Folder folder;
	
	/**
	 * Identificador del documento.
	 */
	private String docID;
	
	/**
	 * Identificador de la página.
	 */
	private String pageID;

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
	public Folder getFolder() {
		return folder;
	}

	/**
	 * @param folder
	 */
	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	/**
	 * @return
	 */
	public String getPageID() {
		return pageID;
	}

	/**
	 * @param pageID
	 */
	public void setPageID(String pageID) {
		this.pageID = pageID;
	}

	/**
	 * @return
	 */
	public String getDocumentContentB64() {
		return documentContentB64;
	}

	/**
	 * @param documentContentB64
	 */
	public void setDocumentContentB64(String documentContentB64) {
		this.documentContentB64 = documentContentB64;
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
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * @param pageName
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

}
