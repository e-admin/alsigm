package ieci.tecdoc.sgm.core.services.mensajes_cortos.dto;

/**
 * Información del adjunto
 *
 */
public class Attachment {

	/**
	 * Nombre del fichero adjunto.
	 */
	private String fileName = null;
	
	/**
	 * Contenido del fichero adjunto.
	 */
	private byte [] content = null;

	
	/**
	 * Constructor.
	 */
	public Attachment() {
		super();
	}

	/**
	 * Constructor.
	 * @param fileName Nombre del fichero adjunto. 
	 * @param content Contenido del fichero adjunto.
	 */
	public Attachment(String fileName, byte[] content) {
		super();
		this.fileName = fileName;
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
}
