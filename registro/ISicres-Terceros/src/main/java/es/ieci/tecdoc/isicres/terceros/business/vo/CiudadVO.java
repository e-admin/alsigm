package es.ieci.tecdoc.isicres.terceros.business.vo;

import javax.validation.constraints.Size;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 *
 * @author IECISA
 *
 */
public class CiudadVO extends Entity {

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

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

	@Size(max=40)
	protected String nombre;

	protected String codigo;

	protected ProvinciaVO provincia;

	private static final long serialVersionUID = -4942548196429217902L;

}
