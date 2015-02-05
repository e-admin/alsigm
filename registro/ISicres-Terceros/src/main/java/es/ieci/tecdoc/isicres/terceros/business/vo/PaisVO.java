package es.ieci.tecdoc.isicres.terceros.business.vo;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 *
 * @author IECISA
 *
 */
public class PaisVO extends Entity {

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	// Members
	protected String nombre;

	protected String codigo;

	private static final long serialVersionUID = -5130959923569845639L;

}
