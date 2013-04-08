package ieci.tecdoc.sgm.core.services.admsesion.backoffice;

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
