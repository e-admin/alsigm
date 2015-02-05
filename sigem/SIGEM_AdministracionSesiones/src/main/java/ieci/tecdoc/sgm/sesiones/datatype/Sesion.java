package ieci.tecdoc.sgm.sesiones.datatype;

import ieci.tecdoc.sgm.sesiones.timer.SesionesCaducadas;

public class Sesion {
		
	private String usuario;
	
	private String idSesion;
	
	private String idEntidad;

	private String datosEspecificos;
	
	private SesionesCaducadas sesionCaducada;
	
	public Sesion(String usuario, String idSesion, String idEntidad) {
		this.usuario = usuario;
		this.idSesion = idSesion;
		this.idEntidad = idEntidad;
		this.datosEspecificos = null;
		this.sesionCaducada = new SesionesCaducadas(usuario + "_" + idSesion);
	}

	public String getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Sesion(String idEntidad) {
		super();
		this.idEntidad = idEntidad;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getDatosEspecificos() {
		return datosEspecificos;
	}

	public void setDatosEspecificos(String datosEspecificos) {
		this.datosEspecificos = datosEspecificos;
	}

	public SesionesCaducadas getSesionCaducada() {
		return sesionCaducada;
	}

	public void setSesionCaducada(SesionesCaducadas sesionCaducada) {
		this.sesionCaducada = sesionCaducada;
	}

}
