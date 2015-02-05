package ieci.tecdoc.sgm.pe;
/*
 * $Id: EntidadBancariaImpl.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
public class EntidadBancariaImpl implements EntidadBancaria {

	protected String idEntidad;
	protected String nombre;

	public String getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
