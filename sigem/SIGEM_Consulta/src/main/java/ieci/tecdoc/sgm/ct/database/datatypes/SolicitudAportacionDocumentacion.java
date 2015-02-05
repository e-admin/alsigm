package ieci.tecdoc.sgm.ct.database.datatypes;

import java.util.Date;

public class SolicitudAportacionDocumentacion {

	private String identificador;
	private String idDocumento;
	private String mensajeParaElCiudadano;
	private String identificadorHito;
	private Date fecha;
	private String numeroExpediente;
	
	public String getNumeroExpediente() {
		return numeroExpediente;
	}
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getIdentificadorHito() {
		return identificadorHito;
	}
	public void setIdentificadorHito(String identificadorHito) {
		this.identificadorHito = identificadorHito;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getMensajeParaElCiudadano() {
		return mensajeParaElCiudadano;
	}
	public void setMensajeParaElCiudadano(String mensajeParaElCiudadano) {
		this.mensajeParaElCiudadano = mensajeParaElCiudadano;
	}

}
