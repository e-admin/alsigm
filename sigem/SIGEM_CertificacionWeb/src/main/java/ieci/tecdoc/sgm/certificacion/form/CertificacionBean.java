package ieci.tecdoc.sgm.certificacion.form;

import org.apache.struts.action.ActionForm;

public class CertificacionBean extends ActionForm{
	private String idUsuario;
	private String idFichero;
	private String idEntidad;
	private String nombreEntidad;
	
	public String getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getIdFichero() {
		return idFichero;
	}
	
	public void setIdFichero(String idFichero) {
		this.idFichero = idFichero;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	
}
