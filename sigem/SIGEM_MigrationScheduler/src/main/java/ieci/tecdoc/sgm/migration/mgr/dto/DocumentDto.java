package ieci.tecdoc.sgm.migration.mgr.dto;

public class DocumentDto {

	/**
	 * Contenido del documento en B64
	 */
	private String contentB64 = null;
	
	/**
	 * Nombre del documento
	 */
	private String documentName = null;
	
	/**
	 * Extensión del documento
	 */
	private String extension = null;
	
	/**
	 * Nombre de la página
	 */
	private String pageName = null;
	
	public String getContentB64() {
		return contentB64;
	}
	public void setContentB64(String contentB64) {
		this.contentB64 = contentB64;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
}
