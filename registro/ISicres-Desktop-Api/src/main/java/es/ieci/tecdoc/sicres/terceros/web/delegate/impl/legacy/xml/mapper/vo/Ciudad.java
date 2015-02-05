package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 *
 * @author IECISA
 *
 */
public class Ciudad extends Entity {

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	protected String codigo;
	protected String nombre;
	protected String idProvincia;
	private static final long serialVersionUID = 6584525060590989461L;

}
