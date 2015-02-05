package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 *
 * @author IECISA
 *
 */
public class Provincia extends Entity {

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

	protected String codigo;
	protected String nombre;
	private static final long serialVersionUID = -2486529222850475360L;

}
