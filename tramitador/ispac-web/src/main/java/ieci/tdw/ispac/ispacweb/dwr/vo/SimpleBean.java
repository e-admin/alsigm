package ieci.tdw.ispac.ispacweb.dwr.vo;

import java.io.Serializable;

public class SimpleBean implements Serializable {

	/**
	 * Clave del bean.
	 */
	private String key = null;
	
	/**
	 * Valor del bean.
	 */
	private String value = null;
	
	/**
	 * Identificador del bean
	 */
	
	private String id=null;
	
	/**
	 * Constructor.
	 */
	public SimpleBean() {
		super();
	}

	
	/**
	 * Constructor.
	 * @param key Clave del bean.
	 * @param value Valor del bean.
	 */
	public SimpleBean(String id, String key, String value) {
		this();
		this.id=id;
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Constructor.
	 * @param key Clave del bean.
	 * @param value Valor del bean.
	 */
	public SimpleBean(String key, String value) {
		this();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
}
