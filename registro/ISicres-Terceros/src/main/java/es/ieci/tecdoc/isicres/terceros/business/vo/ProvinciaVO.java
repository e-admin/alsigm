package es.ieci.tecdoc.isicres.terceros.business.vo;

import javax.validation.constraints.Size;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 *
 * @author IECISA
 *
 */
public class ProvinciaVO extends Entity {

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
	@Size(max=30)
	protected String nombre;

	protected String codigo;

	private static final long serialVersionUID = 4243038324427058924L;

}
