package ieci.tdw.ispac.ispacweb.dwr.vo;

import java.io.Serializable;

public class WebComponent implements Serializable {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del componente.
	 */
	private String id = null;
	
	/**
	 * Título del componente.
	 */
	private String title = null;
	
	/**
	 * Contenido del componente.
	 */
	private String content = null;

	
	/**
	 * Constructor.
	 */
	public WebComponent() {
		super();
	}

	/**
	 * Constructor.
	 * @param id Identificador del componente.
	 * @param title Título del componente.
	 * @param content Contenido del componente.
	 */
	public WebComponent(String id, String title, String content) {
		this();
		setId(id);
		setTitle(title);
		setContent(content);
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
}
