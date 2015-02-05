package ieci.tecdoc.sgm.sesiones.datatype;

import ieci.tecdoc.sgm.sesiones.timer.SesionesCaducadas;

public class Sesion {
		
	private String usuario;
	
	private String idSesion;
	
	private String idEntidad;

	private String datosEspecificos;
	
	private int tipoUsuario;
	
	private SesionesCaducadas sesionCaducada;
	
	public Sesion(String usuario, String idSesion, int tipoUsuario) {
		this.usuario = usuario;
		this.idSesion = idSesion;
		this.idEntidad = null;
		this.datosEspecificos = null;
		this.tipoUsuario = tipoUsuario;
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

	public int getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(int tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	public static final int TIPO_USUARIO_ADMINISTRADOR = 1;
	public static final int TIPO_USUARIO_INTERNO = 2;
}
