/*
 * Creado el 13-jun-2005
 *
 */
package ieci.tdw.ispac.ispaclib.invesicres.registro.vo;

/**
 * @author RAULHC
 *
 */
public class DocumentoVO {
	/** Identificador del documento anexado a la carpeta**/
	private String docId = null;
	/** Identificador de la página del documento **/
	private String pageId = null;
	/** Nombre del documento anexado a la carpeta **/
	private String docName = null;
	/** Nombre de la página del documento **/
	private String pageName = null;
	/** Extension del documento **/
	private String loc = null;
	/** Path al fichero **/
	private String path = null;
	
	/**
	 * @return Retorna docId.
	 */
	public String getDocId() {
		return docId;
	}
	/**
	 * @param docId The docId to set.
	 */
	public void setDocId(String docId) {
		this.docId = docId;
	}
	/**
	 * @return Retorna docName.
	 */
	public String getDocName() {
		return docName;
	}
	/**
	 * @param docName The docName to set.
	 */
	public void setDocName(String docName) {
		this.docName = docName;
	}
	/**
	 * @return Retorna loc.
	 */
	public String getLoc() {
		return loc;
	}
	/**
	 * @param loc The loc to set.
	 */
	public void setLoc(String loc) {
		this.loc = loc;
	}
	/**
	 * @return Retorna pageId.
	 */
	public String getPageId() {
		return pageId;
	}
	/**
	 * @param pageId The pageId to set.
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	/**
	 * @return Retorna pageName.
	 */
	public String getPageName() {
		return pageName;
	}
	/**
	 * @param pageName The pageName to set.
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	/**
	 * @return Retorna path.
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path The path to set.
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
