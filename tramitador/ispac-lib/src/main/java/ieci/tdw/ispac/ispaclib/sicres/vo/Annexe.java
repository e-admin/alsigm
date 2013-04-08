package ieci.tdw.ispac.ispaclib.sicres.vo;

import java.io.Serializable;

public class Annexe implements Serializable {

	/**
	 * Identificador del anexo.
	 */
	private String id = null;
	
	/**
	 * Nombre del anexo.
	 */
	private String name = null;
	
	/**
	 * Extensión del anexo.
	 */
	private String ext = null;
	
	private String bookId = null;
	
	private String folderId = null;
	
	private String docId = null;
	
	private String pageId = null;
	
	
	/**
	 * Constructor.
	 */
	public Annexe() {
		super();
	}

	/**
	 * Constructor.
	 * @param id Identificador del anexo.
	 * @param name Nombre del anexo.
	 * @param ext Extensión del anexo.
	 */
	public Annexe(String id, String name, String ext) {
		this();
		setId(id);
		setName(name);
		setExt(ext);
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getExt() {
		return ext;
	}


	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	
}
