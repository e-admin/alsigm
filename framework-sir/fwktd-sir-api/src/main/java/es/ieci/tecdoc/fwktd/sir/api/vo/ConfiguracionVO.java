package es.ieci.tecdoc.fwktd.sir.api.vo;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 * Información de un parámetro de configuración.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ConfiguracionVO extends Entity {

	private static final long serialVersionUID = 6403196938504175888L;

	/**
	 * Nombre del parámetro.
	 */
	private String nombre = null;

	/**
	 * Descripcion del parámetro.
	 */
	private String descripcion = null;

	/**
	 * Valor del parámetro.
	 */
	private String valor = null;

	/**
	 * Constructor.
	 */
	public ConfiguracionVO() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
