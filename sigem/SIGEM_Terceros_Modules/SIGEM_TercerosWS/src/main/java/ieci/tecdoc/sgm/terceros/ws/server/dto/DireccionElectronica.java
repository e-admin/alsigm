package ieci.tecdoc.sgm.terceros.ws.server.dto;

import java.io.Serializable;

/**
 * Información de la dirección electrónica de un tercero.
 *
 */
public class DireccionElectronica implements Serializable {

	/**
	 * Identificador de la dirección electrónica.
	 */
	private String id;
	
	/**
	 * Tipo de dirección electrónica:
	 */
	private int tipo;
	
	/**
	 * Dirección electrónica.
	 */
	private String direccion;

	
	/**
	 * Constructor.
	 */
	public DireccionElectronica() {
		super();
	}
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
