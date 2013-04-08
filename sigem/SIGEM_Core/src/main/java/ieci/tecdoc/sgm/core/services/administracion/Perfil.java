package ieci.tecdoc.sgm.core.services.administracion;

/*
 * $Id: Perfil.java,v 1.1.2.1 2008/04/14 09:11:07 afernandez Exp $
 */

public class Perfil {

	private String idEntidad;
	private String idAplicacion;
	private String idUsuario;
	
	public String getIdAplicacion() {
		return idAplicacion;
	}
	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}
	public String getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
}
