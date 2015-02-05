package ieci.tecdoc.sgm.core.services.registro;

/**
 * Informacion de un document
 * 
 */
public class DocumentInfo {
	/**
	 * Nombre del documento
	 */
	protected String documentName = null;

	/**
	 * Nombre del fichero
	 */
	protected String fileName = null;

	/**
	 * Nombre de la página del documento
	 */
	protected String pageName = null;

	/**
	 * Contenido del fichero
	 */
	protected byte[] documentContent = null;

	/**
	 * Extensión del fichero
	 */
	protected String extension = null;

	/**
	 * @return
	 */
	public byte[] getDocumentContent() {
		return documentContent;
	}

	/**
	 * @param documentContent
	 */
	public void setDocumentContent(byte[] documentContent) {
		this.documentContent = documentContent;
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
