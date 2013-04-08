package ieci.tecdoc.sgm.core.services.idioma;

import java.io.Serializable;

public class Idioma implements Serializable {

	private String codigo;
	private String descripcion;
	
	public Idioma() {
		this.codigo = null;
		this.descripcion = null;
	}
	
	public Idioma(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
