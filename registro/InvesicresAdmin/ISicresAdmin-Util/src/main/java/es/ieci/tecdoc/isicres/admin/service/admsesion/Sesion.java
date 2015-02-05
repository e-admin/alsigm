package es.ieci.tecdoc.isicres.admin.service.admsesion;

public class Sesion {
		
	private String usuario;
	
	private String idSesion;
	
	private String idEntidad;

	private String datosEspecificos;
	
	public Sesion() {
		this.usuario = null;
		this.idSesion = null;
		this.idEntidad = null;
		this.datosEspecificos = null;
	}
	/*
	public Sesion() {
		this.usuario = "administrador";
		this.idSesion = "NF0BE770017A11DFBFFBFB3A7379B40A";
		this.idEntidad = "ISicres";
		this.datosEspecificos = "Usuario Administrador";
	}
	*/
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

}
