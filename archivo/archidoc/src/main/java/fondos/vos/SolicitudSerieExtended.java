package fondos.vos;

import fondos.model.SolicitudSerie;

public class SolicitudSerieExtended extends SolicitudSerie {
	String nombreUsuarioSolicitante = null;
	String apellidosUsuarioSolicitante = null;
	String nombreUsuarioGestor = null;
	String apellidosUsuarioGestor = null;

	public String getApellidosUsuarioGestor() {
		return apellidosUsuarioGestor;
	}

	public void setApellidosUsuarioGestor(String apellidosUsuarioGestor) {
		this.apellidosUsuarioGestor = apellidosUsuarioGestor;
	}

	public String getApellidosUsuarioSolicitante() {
		return apellidosUsuarioSolicitante;
	}

	public void setApellidosUsuarioSolicitante(
			String apellidosUsuarioSolicitante) {
		this.apellidosUsuarioSolicitante = apellidosUsuarioSolicitante;
	}

	public String getNombreUsuarioGestor() {
		return nombreUsuarioGestor;
	}

	public void setNombreUsuarioGestor(String nombreUsuarioGestor) {
		this.nombreUsuarioGestor = nombreUsuarioGestor;
	}

	public String getNombreUsuarioSolicitante() {
		return nombreUsuarioSolicitante;
	}

	public void setNombreUsuarioSolicitante(String nombreUsuarioSolicitante) {
		this.nombreUsuarioSolicitante = nombreUsuarioSolicitante;
	}
}
