package ieci.tecdoc.sgm.registropresencial.ws.server;

/**
 * 
 * Almacena un fichero e información sobre el mismo
 * 
 */
public class Page {

	/**
	 * Nombre de la página
	 */
	private String pageName = null;

	/**
	 * Identificador del registro al que pertenece esta página
	 */
	private String folderId = null;

	/**
	 * Identificador del documento al que pertenece esta página
	 */
	private String docID = null;

	/**
	 * Identificador de la página.
	 */
	private String pageID = null;

	/**
	 * Identificador del fichero que está almacenado en esta página
	 */
	private String fileID = null;

	/**
	 * Identificador del volumen en el que se encuentra el fichero. Los ficheros
	 * se depositan en repositorios (normalmente un FTP), estos se dividen en
	 * volúmenes.
	 */
	private String volidID = null;

	/**
	 * Ruta de acceso al fichero dentro de su volumen
	 */
	private String loc = null;

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
