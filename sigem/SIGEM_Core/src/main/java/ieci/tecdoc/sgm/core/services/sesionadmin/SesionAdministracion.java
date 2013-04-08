package ieci.tecdoc.sgm.core.services.sesionadmin;

import java.util.Date;

public class SesionAdministracion {

	private String idSesion;
	private Date fechaLogin;
	private Date fechaActualizacion;
	private String idEntidad;
	private String idUsuario;
	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Date getFechaLogin() {
		return fechaLogin;
	}
	public void setFechaLogin(Date fechaLogin) {
		this.fechaLogin = fechaLogin;
	}
	public String getIdSesion() {
		return idSesion;
	}
	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}
	
	
}
