package ieci.tdw.ispac.api.expedients;

import java.io.InputStream;

public class Document {
	
	private String id;
	private String code;
	private String name;
	private InputStream content;
	private String extension;
	private int length;
	
	public Document() {
	}
	
	public Document (String id,
					 String code,
					 String name,
					 InputStream content,
					 String extension,
					 int length) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.content = content;
		this.extension = extension;
		this.length = length;
	}		

	public Document(String code, String name, InputStream content,
			String extension, int length) {
		this.id = null;
		this.code = code;
		this.name = name;
		this.content = content;
		this.extension = extension;
		this.length = length;
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
	public InputStream getContent() {
		return content;
	}
	/**
	 * @param content The content to set.
	 */
	public void setContent(InputStream content) {
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
	 * @return Returns the length.
	 */
	public int getLength() {
		return length;
	}
	/**
	 * @param length The length to set.
	 */
	public void setLength(int length) {
		this.length = length;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}