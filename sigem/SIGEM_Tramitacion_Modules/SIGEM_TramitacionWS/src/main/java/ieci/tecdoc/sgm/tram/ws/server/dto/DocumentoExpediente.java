package ieci.tecdoc.sgm.tram.ws.server.dto;


public class DocumentoExpediente {
	
	private String id;
	private String code;
	private String name;
	private byte[] content;
	private String extension;
	private int lenght;
	
	public DocumentoExpediente() {
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return Returns the content.
	 */
	public byte[] getContent() {
		return content;
	}
	/**
	 * @param content The content to set.
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	/**
	 * @return Returns the extension.
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @param extension The extension to set.
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the lenght.
	 */
	public int getLenght() {
		return lenght;
	}
	/**
	 * @param lenght The lenght to set.
	 */
	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
