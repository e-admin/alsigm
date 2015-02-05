package ieci.tdw.ispac.ispaclib.sicres.vo;

import ieci.tdw.ispac.api.IRegisterAPI;

import java.io.Serializable;

public class RegisterBook implements Serializable {

	/**
	 * Identificador del libro.
	 */
	private String id = null;
	
	/**
	 * Nombre del libro.
	 */
	private String name = null;
	
	/**
	 * Tipo de libro.
	 */
	private int type = IRegisterAPI.BOOK_TYPE_INPUT;


	/**
	 * Constructor.
	 */
	public RegisterBook() {
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


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}
	
}
