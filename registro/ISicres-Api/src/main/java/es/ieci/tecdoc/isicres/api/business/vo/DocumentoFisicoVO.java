package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class DocumentoFisicoVO extends BaseDocumentoVO {

	private static final long serialVersionUID = -3309036548414558789L;

	protected String name;

	protected String extension;

	private byte[] content;

	/**
	 * cadena que identifica donde obtener el recurso fisico
	 * 
	 */
	private java.lang.String location;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return (null != extension) ? extension : "";
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public java.lang.String getLocation() {
		return location;
	}

	public void setLocation(java.lang.String location) {
		this.location = location;
	}

}
