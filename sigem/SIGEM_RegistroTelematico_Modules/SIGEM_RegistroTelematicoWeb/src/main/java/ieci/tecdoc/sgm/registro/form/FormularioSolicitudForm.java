package ieci.tecdoc.sgm.registro.form;

import org.apache.struts.action.ActionForm;

public class FormularioSolicitudForm extends ActionForm{
	private String documentoIdentidad;
	private String nombreSolicitante;
	private String emailSolicitante;
	private String organoDestinatario;
	private String datosEspecificos;
	
	public FormularioSolicitudForm(){
		this.documentoIdentidad = "";
		this.nombreSolicitante = "";
		this.emailSolicitante = "";
		this.organoDestinatario = "";
		this.datosEspecificos = "";
	}

	public String getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public void setDocumentoIdentidad(String documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}

	public String getEmailSolicitante() {
		return emailSolicitante;
	}

	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}

	public String getNombreSolicitante() {
		return nombreSolicitante;
	}

	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}

	public String getOrganoDestinatario() {
		return organoDestinatario;
	}

	public void setOrganoDestinatario(String organoDestinatario) {
		this.organoDestinatario = organoDestinatario;
	}

	public String getDatosEspecificos() {
		return datosEspecificos;
	}

	public void setDatosEspecificos(String datosEspecificos) {
		this.datosEspecificos = datosEspecificos;
	}
	
	private final static long serialVersionUID = 0;
}
