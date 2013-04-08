package ieci.tdw.ispac.ispaclib.producers.vo;

import java.io.Serializable;

public class Producer implements Serializable {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del productor.
	 */
	private String id = null;
	
	/**
	 * Nombre del productor.
	 */
	private String name = null;
	
	
	/**
	 * Constructor.
	 */
	public Producer() {
		super();
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

}
