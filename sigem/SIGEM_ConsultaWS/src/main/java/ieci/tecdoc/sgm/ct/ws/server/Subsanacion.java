package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: Subsanacion.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Subsanacion extends RetornoServicio {
	private String identificador;
	private String idDocumento;
	private String mensajeParaElCiudadano;
	private String identificadorHito;
	private String fecha;
	private String numeroExpediente;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
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
	public String getMensajeParaElCiudadano() {
		return mensajeParaElCiudadano;
	}
	public void setMensajeParaElCiudadano(String mensajeParaElCiudadano) {
		this.mensajeParaElCiudadano = mensajeParaElCiudadano;
	}
	public String getNumeroExpediente() {
		return numeroExpediente;
	}
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}	
}
