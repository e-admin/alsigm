package ieci.tecdoc.sgm.core.services.registro;

/**
 * Información de una página de un documento asociada a un registro
 * 
 */
public class Page {

	/**
	 * Nombre de la pagina
	 */
	protected String pageName = null;

	/**
	 * Identificador del registro al que esta asociado el documento
	 */
	protected String folderId = null;

	/**
	 * Identificador del documento al que pertence la página
	 */
	protected String docID = null;

	/**
	 * Identificador de la página
	 */
	protected String pageID = null;

	/**
	 * Identificador del fichero
	 */
	protected String fileID = null;

	/**
	 * Identificador del volumen de la pagina
	 */
	protected String volidID = null;

	/**
	 * Localización relativa al volumen
	 */
	protected String loc = null;

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
	public String getPageName() {
		return pageName;
	}

	/**
	 * @param pageName
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * @return
	 */
	public String getFileID() {
		return fileID;
	}

	/**
	 * @param fileID
	 */
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	/**
	 * @return
	 */
	public String getLoc() {
		return loc;
	}

	/**
	 * @param loc
	 */
	public void setLoc(String loc) {
		this.loc = loc;
	}

	/**
	 * @return
	 */
	public String getVolidID() {
		return volidID;
	}

	/**
	 * @param volidID
	 */
	public void setVolidID(String volidID) {
		this.volidID = volidID;
	}
}
